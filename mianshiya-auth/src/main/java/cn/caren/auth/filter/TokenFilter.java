package cn.caren.auth.filter;


import cn.caren.auth.bean.TokenProperties;
import cn.caren.auth.config.TokenProvider;
import cn.caren.auth.manager.AuthCacheManager;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
public class TokenFilter extends GenericFilterBean {

    private TokenProperties properties;




    public TokenFilter(TokenProperties properties) {
        this.properties = properties;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String token = TokenProvider.resolveToken(request);
        // token不为空
        if (StrUtil.isNotEmpty(token)) {
            // token有效
            if(AuthCacheManager.existToken(token)){
                TokenProvider.checkToken(token);
                Authentication authentication = TokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                TokenProvider.checkRenewal(token);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

}
