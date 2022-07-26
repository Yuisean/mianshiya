package cn.caren.auth.bean;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class LoginQuery {

    @Email
    private String email;

    private String password;

    private String code;

    private String uuid;
}
