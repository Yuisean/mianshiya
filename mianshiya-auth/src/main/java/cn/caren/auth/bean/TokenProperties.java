package cn.caren.auth.bean;

import lombok.Data;

@Data
public class TokenProperties {

    /**
     * token的过期时间 单位秒
     */
    private Long timeOut;

    /**
     * token 名称
     */
    private String cacheName;

    /**
     * 请求头
     */
    private String header;

    /**
     * frefix bearer
     */
    private String prefix;

    /**
     * 续期检查范围 秒
     */
    private Long detect;

    /**
     * 续期时间 秒
     */
    private Long renew;

    /**
     * 密钥
     */
    private String base64Secret;

    public String getCacheName() {
        return cacheName + ":";
    }

    public String getPrefix() {
        return prefix + " ";
    }

    public byte[] getSigner(){
        return base64Secret.getBytes();
    }
}
