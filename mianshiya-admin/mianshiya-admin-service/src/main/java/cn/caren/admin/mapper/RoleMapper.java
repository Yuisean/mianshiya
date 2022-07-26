package cn.caren.admin.mapper;

import cn.caren.admin.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author caren
 * @data 2022/7/26 16:05
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据id查询角色信息
     * @param id 用户id
     * @return List<Role>
     */
    List<Role> getRolesByUserId(Long id);
}
