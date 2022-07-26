package cn.caren.auth.utils;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author caren
 * @data 2022/7/26 23:41
 */
public class AuthUtil {

    public static String getName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public static Set<String> getAuthorities(){
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
    }

    public static Object getPrincipal(){
        SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
