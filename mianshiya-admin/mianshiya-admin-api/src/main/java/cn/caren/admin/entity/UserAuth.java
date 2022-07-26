package cn.caren.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("admin_user_auth")
public class UserAuth implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;


    private String email;

    private String password;

    private Long userId;

    /**
     * 用户是否启用。1:未启用，0:启用
     */
    @TableField("is_enabled")
    private Boolean enabled;


    private Date createdTime;


    private Date updatedTime;
}
