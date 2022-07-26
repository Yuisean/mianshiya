package cn.caren.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author caren
 * @data 2022/7/26 14:03
 */
@Data
@TableName("admin_role")
public class Role {


    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色简介
     */
    private String profile;

    private Date createdTime;

    private Date updatedTime;
}
