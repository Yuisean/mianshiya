package cn.caren.auth.manager;

import cn.caren.auth.bean.TokenProperties;
import cn.caren.core.exception.BizException;
import cn.caren.core.exception.ExceptionType;
import cn.caren.core.factory.RedisTemplateFactory;
import cn.caren.core.spring.SpringContextHolder;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

/**
 * @author caren
 * @data 2022/7/27 01:28
 */
@Slf4j
public class AuthCacheManager {

    private static final TokenProperties PROPERTIES = SpringContextHolder.getBean(TokenProperties.class);

    private static final ValueOperations<String, Object> STRINGOPERATIONS = RedisTemplateFactory.stringOperations();

    private static final RedisTemplate<String, Object> TEMPLATE = RedisTemplateFactory.templateOperations();

    private static final String LOGIN_CODE_CACHE_NAME = "auth:code:";

    /**
     * 添加token缓存
     *
     * @param token token
     */
    public static void setTokenToCache(String token) {
        try {
            // 值为空，不需要存储任何数据。此处仅仅用来维护token的有效期
            STRINGOPERATIONS.set(PROPERTIES.getCacheName() + token, "", PROPERTIES.getTimeOut(), TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("redis set token: [{}], error msg:[{}]", token, e.getMessage());
            // TODO: 2022/7/26 global exception
        }
    }


    /**
     * 添加登录验证码
     *
     * @param code 验证码
     * @param uuid 缓存唯一标识
     */
    public static void insertLoginCode(String code, String uuid) {
        STRINGOPERATIONS.set(LOGIN_CODE_CACHE_NAME + uuid, code);
    }

    /**
     * 检查登录验证码
     *
     * @param code 验证码
     * @param uuid 唯一标识
     */
    public static void checkLoginCode(String code, String uuid) {
        String redisCode = (String) STRINGOPERATIONS.get(LOGIN_CODE_CACHE_NAME + uuid);
        // 验证码不存在
        if (StrUtil.isEmpty(redisCode)) {
            log.warn("login code not found");
            throw new BizException(ExceptionType.LOGIN_CODE_EXPIRE_A0014);
        }
        //验证码错误
        if (!code.equalsIgnoreCase(redisCode)) {
            log.warn("login code validate fail");
            throw new BizException(ExceptionType.LOGIN_CODE_EXPIRE_A0014);
        }
    }

    /**
     * 删除登录验证码
     *
     * @param uuid 唯一标识
     */
    public static void deleteLoginCode(String uuid) {
        try {
            TEMPLATE.delete(LOGIN_CODE_CACHE_NAME + uuid);
        } catch (Exception e) {
            log.error("redis delete login code key:[{}],error msg:[{}]", LOGIN_CODE_CACHE_NAME + uuid, e.getMessage());
            throw new BizException(ExceptionType.CACHE_DELETE_KEY_FAIL_C0010);
        }
    }


    /**
     * 阻塞表单重复提交
     *
     * @param uuid
     */
    public static void blockInputRepeat(String uuid) {
        // TODO: 2022/7/27 redisson 分布式锁实现表单重复提交
    }
}
