package cn.caren.admin.dto;

import cn.caren.admin.entity.Role;
import cn.hutool.core.collection.ListUtil;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author caren
 * @data 2022/7/26 03:24
 */
@Data
public class UserInfoDto implements Serializable, UserDetails {

    private Integer authId;

    private Long id;

    private String email;

    private String password;

    private String nickname;

    private String avatar;

    private String profile;

    private Boolean enabled;

    /**
     * 0:男，1：女
     */
    private Integer gender;

    /**
     * 积分
     */
    private Integer score;

    /**
     * 0：在校，1：求职，2：工作
     */
    private Integer status;

    /**
     * 角色
     */
    private List<Role> roles;


    private Date createdTime;


    private Date updatedTime;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (roles.isEmpty()){
            // 角色为空，附空值
            return ListUtil.empty();
        }
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
