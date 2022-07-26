package cn.caren.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author caren
 * @data 2022/7/26 03:35
 */
@Data
@TableName("admin_user_info")
public class UserInfo {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String nickname;

    /**
     * 简介
     */
    private String profile;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 0:男，1：女
     */
    private Integer gender;


    private Integer level;


    /**
     * 积分
     */
    private Integer score;

    /**
     * 0：在校，1：求职，2：工作
     */
    private Integer status;


    private Date createdTime;

    private Date updatedTime;
}
