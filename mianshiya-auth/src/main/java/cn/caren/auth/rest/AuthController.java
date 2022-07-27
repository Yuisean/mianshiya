package cn.caren.auth.rest;

import cn.caren.auth.annotation.rest.AnonymousGetMapping;
import cn.caren.auth.annotation.rest.AnonymousPostMapping;
import cn.caren.auth.bean.LoginQuery;
import cn.caren.auth.config.TokenProvider;
import cn.caren.auth.manager.AuthCacheManager;
import cn.caren.auth.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author caren
 */

@RestController
@Slf4j
public class AuthController {



    @Resource
    AuthenticationManager authenticationManager;

    @Resource
    AuthService authService;



    /**
     * 根据邮箱和密码登录账号，返回token值，由前端保存
     * @param query 登录账号
     * @return token
     */
    @AnonymousPostMapping(value = "/auth/login")
    public ResponseEntity<Map<String, String>> login(@Validated @RequestBody LoginQuery query) {
        // 1.验证码确认，并且防止表单重复提交
        authService.checkLoginCode(query.getCode(),query.getUuid());
        // 2.1 认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(query.getEmail(), query.getPassword());
        // 2.2 认证管理器，自带密码校验
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        // 2.3 存入上下文
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        // 3. 创建token
        String token = TokenProvider.createToken(authenticate.getPrincipal());
        // 4. 存入redis，维护token 过期时间
        AuthCacheManager.setTokenToCache(token);
        Map<String, String> map = new HashMap<>(1);
        map.put("token",token);
        return ResponseEntity.ok(map);
    }


    /**
     * 发送验证码
     * @return /
     */
    @AnonymousGetMapping("/send_code")
    public ResponseEntity<Map<String, String>> sendCode() {
        return ResponseEntity.ok(authService.sendLoginCode());
    }

    /**
     * 退出登录，清空用户缓存和token缓存
     * @param request /
     * @return /
     */
    @GetMapping("/out")
    public ResponseEntity<HttpStatus> logout(HttpServletRequest request) {
        authService.logout(TokenProvider.resolveToken(request));
        return ResponseEntity.ok(HttpStatus.OK);
    }




    //=======================================================用户登录权限测试=========================================================

    @GetMapping("/test/role")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String testRole(){
        return "success";
    }


    @GetMapping("/test/normal")
    public String testNormal(){
        return "success";
    }

    @GetMapping("/test")
    @PreAuthorize("hasRole('ROLE_123123')")
    public String test(){
        return "success";
    }


}

