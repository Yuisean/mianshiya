package cn.caren.auth.config;


import cn.caren.admin.dto.UserInfoDto;
import cn.caren.auth.bean.TokenPayload;
import cn.caren.auth.bean.TokenProperties;
import cn.caren.core.exception.BizException;
import cn.caren.core.exception.ExceptionType;
import cn.caren.core.factory.RedisTemplateFactory;
import cn.caren.core.spring.SpringContextHolder;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.exceptions.ValidateException;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.JWTValidator;
import cn.hutool.jwt.signers.JWTSignerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
public class TokenProvider {


    private static final HashOperations<String, Object, Object> HASH_OPERATIONS = RedisTemplateFactory.hashOperations();

    private static final ValueOperations<String, Object> STRING_OPERATIONS = RedisTemplateFactory.stringOperations();

    private static final RedisTemplate<String, Object> TEMPLATE = RedisTemplateFactory.templateOperations();

    private static TokenProperties properties = SpringContextHolder.getBean(TokenProperties.class);

    private static UserDetailsService userDetailsService = SpringContextHolder.getBean("detailsServiceImpl");

    /**
     * 创建token
     *
     * @param item token 存储内容
     * @return token
     */
    public static String createToken(Object item) {
        TokenPayload tokenPayload = BeanUtil.copyProperties(item, TokenPayload.class);
        tokenPayload.setUserId(((UserInfoDto) item).getId());
        Map<String, Object> claims = BeanUtil.beanToMap(tokenPayload);
        return JWT.create()
                // 设置签名
                .setKey(properties.getSigner())
                .addPayloads(claims)
                .sign();
    }

    /**
     * 删除token
     *
     * @param token /
     */
    public static void deleteToken(String token) {
        String key = properties.getCacheName() + token;
        try {
            TEMPLATE.delete(key);
        } catch (Exception e) {
            log.error("redis delete token key:[{}],error msg:[{}]", key, e.getMessage());
            throw new BizException(ExceptionType.CACHE_DELETE_KEY_FAIL_C0010,e.getMessage());
        }
    }


    /**
     * 检查 token 是否符合编码
     *
     * @param token /
     */
    public static void checkToken(String token) {
        try {
            JWTValidator.of(token).validateAlgorithm(JWTSignerUtil.hs256(properties.getSigner()));
        } catch (ValidateException e) {
            log.error("check token: [{}],error msg:[{}]", token, e.getMessage());
            throw new BizException(ExceptionType.TOKEN_VALIDATE_ERROR_A0013, e.getMessage());
        }
    }


    /**
     * 解析token，获取 payload
     *
     * @param token token
     * @return Map<String, Object>
     */
    public static Map<String, Object> parseToken(String token) {
        try {
            JWTValidator.of(token).validateAlgorithm(JWTSignerUtil.hs256(properties.getSigner()));
        } catch (ValidateException e) {
            log.error("token: {}, error msg: {}", token, e.getMessage());
        }
        JSONObject claimsJson = JWTUtil.parseToken(token).getPayload().getClaimsJson();
        return BeanUtil.beanToMap(claimsJson);
    }


    /**
     * token 续期
     *
     * @param token 需要检查的token
     */
    public static void checkRenewal(String token) {
        // 判断是否续期token,计算token的过期时间
        long time = TEMPLATE.getExpire(properties.getCacheName() + token);
        Date expireDate = DateUtil.offset(new Date(), DateField.SECOND, (int) time);
        //判断当前时间与过期时间的时间差
        long differ = (expireDate.getTime() - System.currentTimeMillis()) / 1000;
        //如果在续期检查的范围内，则续期
        if (differ <= properties.getDetect()) {
            long renew = time + properties.getRenew();
            TEMPLATE.expire(properties.getCacheName() + token, renew, TimeUnit.SECONDS);
        }
    }

    /**
     * 解析 request请求获取 token
     *
     * @param request 请求
     * @return token
     */
    public static String resolveToken(HttpServletRequest request) {
        String tokenBearer = request.getHeader(properties.getHeader());
        if (StrUtil.isNotBlank(tokenBearer) && tokenBearer.startsWith(properties.getPrefix())) {
            return tokenBearer.replace(properties.getPrefix(), "");
        }
        return null;
    }

    /**
     * 根据 token 完成认证
     *
     * @param token /
     * @return /
     */
    public static Authentication getAuthentication(String token) {
        JSONObject json = JWTUtil.parseToken(token).getPayload().getClaimsJson();
        TokenPayload tokenPayload = json.toBean(TokenPayload.class);
        UserDetails userDetails = userDetailsService.loadUserByUsername(tokenPayload.getEmail());
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), token, userDetails.getAuthorities());
        log.info("query param: {},return: {}", token, authenticationToken);
        return authenticationToken;
    }

}
