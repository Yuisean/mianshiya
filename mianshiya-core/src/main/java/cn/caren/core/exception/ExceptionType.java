package cn.caren.core.exception;

public enum ExceptionType {

    /**
     * 系统内部错误
     */
    SYSTEM_INNRE_ERROR_B1000("B1000", "系统内部错误"),
    /**
     * 请求错误
     */
    BAD_REQUEST_A1000("A1000", "请求错误"),

    /**
     * 未登录
     */
    UNAUTHORIZED_A0010("A0010", "用户未登录"),
    /**
     * 无权操作
     */
    FORBIDDEN_A0011("A0011", "无权操作"),
    /**
     * 用户名或密码错误
     */
    USER_PASSWORD_NOT_MATCH_A0012("A0012", "用户名或密码错误"),

    /**
     * token认证失败
     */
    TOKEN_VALIDATE_ERROR_A0013("A0013","token认证失败"),

    /**
     * 验证码过期
     */
    LOGIN_CODE_EXPIRE_A0014("A0014", "验证码过期或不存在"),


    /**
     * 缓存删除失败
     */
    CACHE_DELETE_KEY_FAIL_C0010("C0010","缓存删除失败"),

    /**
     * 缓存添加失败
     */
    CACHE_INSERT_ITEM_FAIL_C0011("C0011","缓存添加失败"),

    /**
     * 缓存验证key失败
     */
    CACHE_EXIST_FAIL_C0012("C0012","验证缓存key发生错误");



    private final String code;
    private final String message;


    ExceptionType(String code, String message) {
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
