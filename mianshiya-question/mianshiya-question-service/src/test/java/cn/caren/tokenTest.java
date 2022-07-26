package cn.caren;

import cn.caren.auth.bean.TokenPayload;
import cn.caren.auth.bean.TokenProperties;
import cn.caren.auth.config.TokenProvider;
import cn.caren.core.utils.RedisUtil;
import cn.hutool.core.bean.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;
import java.util.Map;

@SpringBootTest
@Slf4j
public class tokenTest {


//    private String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHQiOiIxIiwicm9sZXMiOiJyMDEscjAyIiwibmFtZSI6Im1hbG9uZyIsImlkIjoiMTIzIn0.0ANBvz0zbnmF1nMTZrLddTOyYANf8YHc3e_0cJIYdqk";

//    private String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDJwmKjcwYnIEIv4PawQ1iBoRt50NKeY98FTSUx9HRi2hfBRQxhhcRF+7/yJRZspQ5+4ed6+rX3A1N9eMX+G/XcnfEnbcLKmP5kg3hckOWWRJLSV532XLqvvhWMJaqGL7T92nViDIkbqone2NgR9lHn+K/VT58TUy1xXV6MRd0jvQIDAQAB";

//    private String privatekey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMnCYqNzBicgQi/g9rBDWIGhG3nQ0p5j3wVNJTH0dGLaF8FFDGGFxEX7v/IlFmylDn7h53r6tfcDU314xf4b9dyd8SdtwsqY/mSDeFyQ5ZZEktJXnfZcuq++FYwlqoYvtP3adWIMiRuqid7Y2BH2Uef4r9VPnxNTLXFdXoxF3SO9AgMBAAECgYB08F69f7CkQtU57PjuZspIr/k93KQGu7oK15DkaFyA4wl7sqqfZ6nAKk8xaP+bTvuc3RCOvftzxRDCG5seUlRoV4aYLifhECVuVgFCDnXg9GkxyYIQ+j5c7KOs3Nk+iaHhk61dXLQ8rtAPAJEanEEt1QrUiQ0ykGya2Xn1ZnAc3QJBAPH+2YGPoJBKiuWa+2dVrn7hnHusRmJvmLtyoc/beMKPmeF0/XjxAsc8R7JSH3tOWw8eXrSv2D98PdESocI3s8sCQQDVb3BPLQC4fg1W4xZlbfgbWa0oN02ei9JZJbgPzFICI+EkRQAw8amgB/dD2lMWwHAZeZFgFIlYZnjPMbNC2WWXAkEA3blCcqo1VF9oOg2xUTG5KrnuVYY4CxLy3rSeO8N8Ns+6D0/QulIg2ImLq4ABJkpZtlV+MpF3Ulu0i+5FVZ0R1wJAH7UWciy1VrMjOC00Po0/tpBz8O2GUm9jKGs6FN69sCO0Zrb5eEvubx+/1AyzG0YrYR12Q1fJr6BJgj5X6y5wsQJAQjVKkn2Qo+t00HdaGVZQaYrJRZhSXNSLWA1wBprFaZZ7V3zY8lrn0eiUAZB9/mtWoU3WPCBDFK+cZ5nAbRhFZQ==";


    @Resource
    PasswordEncoder passwordEncoder;


    @Resource
    RedisUtil redisUtil;

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Resource
    RedisTemplate<String,Object> template;

    @Resource
    TokenProperties properties;

    @Test
    public void asdas() {
        Map<String, Object> map = TokenProvider.parseToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.e30.ymShMSxovyE5nDsG4XkGNVp2IHG8onpVb1VD1U7E6-4");
        System.out.println(BeanUtil.toBean(map, TokenPayload.class));

    }


}
