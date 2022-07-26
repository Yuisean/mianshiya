package cn.caren.auth.config;


import cn.caren.auth.bean.TokenProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "jwt.token")
    TokenProperties tokenProperties() {
        return new TokenProperties();
    }
}
