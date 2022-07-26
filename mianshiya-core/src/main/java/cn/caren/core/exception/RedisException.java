package cn.caren.core.exception;

import lombok.Getter;

/**
 * @author caren
 * @data 2022/7/26 20:41
 */
@Getter
public enum RedisException {

    /**
     * redis 字符串类型，添加失败
     */
    REDIS_SET_STRING_ERROR("R1001","redis string type set error");


    private final String code;
    private final String message;


    RedisException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
