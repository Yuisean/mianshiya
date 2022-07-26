package cn.caren.admin.manager;

import cn.caren.admin.dto.UserInfoDto;
import cn.caren.core.exception.BizException;
import cn.caren.core.exception.ExceptionType;
import cn.caren.core.factory.RedisTemplateFactory;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author caren
 * @data 2022/7/26 03:59
 */
@Slf4j
public class UserInfoManager {

    private static final HashOperations<String, Object, Object> HASH_OPERATIONS = RedisTemplateFactory.hashOperations();

    private static final RedisTemplate<String, Object> TEMPLATE = RedisTemplateFactory.templateOperations();


    /**
     * 添加用户信息缓存
     *
     * @param userInfoDto 缓存内容
     * @param email       缓存唯一标识
     */
    public static void setUserInfo(String email, UserInfoDto userInfoDto) {
        String key = constants.USER_INFO_CACHE.getName() + email;
        // 缓存失效时间，单位秒。随机时间防止雪崩，最小30分钟 最大50分钟。
        final Integer time = constants.USER_INFO_CACHE.getTime() + RandomUtil.randomInt(1200, 2400);
        try {
            Map<String, Object> map = BeanUtil.beanToMap(userInfoDto);
            HASH_OPERATIONS.putAll(key, map);
            TEMPLATE.expire(key, time, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("redis hmset key:[{}],value:[{}],error msg:[{}]", key, userInfoDto, e.getMessage());
            throw new BizException(ExceptionType.CACHE_INSERT_ITEM_FAIL_C0011, e.getMessage());
        }
    }

    public static void delUserInfo(String email) {
        String key = constants.USER_INFO_CACHE.getName() + email;
        try {
            TEMPLATE.delete(key);
        } catch (Exception e) {
            log.error("redis delete key:[{}] error msg:[{}]", key, e.getMessage());
            throw new BizException(ExceptionType.CACHE_DELETE_KEY_FAIL_C0010,e.getMessage());
        }
    }

    /**
     * 获取用户信息
     *
     * @param email 缓存唯一标识
     * @return UserInfoDto
     */
    public static UserInfoDto getUserInfoAll(String email) {
        String key = constants.USER_INFO_CACHE.getName() + email;
        Map<Object, Object> map = HASH_OPERATIONS.entries(key);
        return BeanUtil.toBean(map, UserInfoDto.class);
    }

    @Getter
    private enum constants {

        /**
         * 用户信息
         */
        USER_INFO_CACHE("user:info:", 60 * 10);

        private String name;
        private Integer time;

        constants(String name, Integer time) {
            this.name = name;
            this.time = time;
        }
    }

}
