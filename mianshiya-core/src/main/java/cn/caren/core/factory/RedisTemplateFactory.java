package cn.caren.core.factory;

import cn.caren.core.spring.SpringContextHolder;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

/**
 * RedisTemplate 工厂类
 * @author caren
 * @data 2022/7/26 18:59
 */
public class RedisTemplateFactory {

    private static final RedisTemplate<String,Object> template = SpringContextHolder.getBean(RedisTemplate.class,"redisTemplate");

    public static HashOperations<String, Object, Object> hashOperations(){
        return template.opsForHash();
    }

    public static ValueOperations<String, Object> stringOperations(){
         return template.opsForValue();
    }

    public static RedisTemplate<String,Object> templateOperations(){
        return template;
    }
}
