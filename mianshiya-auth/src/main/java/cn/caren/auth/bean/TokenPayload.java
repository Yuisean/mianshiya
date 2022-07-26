package cn.caren.auth.bean;

import lombok.Data;

/**
 * token的payload 信息
 * 存这三个字段是因为修改的几率机会为0
 * 个人习惯问题，不太喜欢在token的payload中存放其他私密信息。
 * 一部分是涉及到信息的安全问题，其次是当我们修改个人信息时，token的payload 不好去同步。所以这里就存放不易修改或不能修改的字段。个人信息都靠redis维护
 * @author caren
 * @data 2022/7/26 15:20
 */
@Data
public class TokenPayload {

    private Long userId;

    private Integer authId;

    private String email;
}
