package cn.caren;

import cn.caren.core.spring.SpringContextHolder;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MapperScan("cn.caren.**.mapper")
public class MsyApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsyApplication.class, args);
    }

    /**
     * 初始化 上下文持有者.
     * @return
     */
    @Bean
    public SpringContextHolder springContextHolder(){
        return new SpringContextHolder();
    }
}
