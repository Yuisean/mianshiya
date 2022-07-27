package cn.caren.auth.bean;

import lombok.Data;

@Data
public class LoginQuery {

    private String email;

    private String password;

    private String code;

    private String uuid;
}
