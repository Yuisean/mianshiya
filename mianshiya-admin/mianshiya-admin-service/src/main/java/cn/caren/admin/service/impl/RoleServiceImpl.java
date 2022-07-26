package cn.caren.admin.service.impl;

import cn.caren.admin.entity.Role;
import cn.caren.admin.mapper.RoleMapper;
import cn.caren.admin.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author caren
 * @data 2022/7/26 16:05
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Resource
    RoleMapper roleMapper;

    @Override
    public List<Role> getRolesByUserId(Long id) {
        List<Role> roles = roleMapper.getRolesByUserId(id);
        return roles;
    }
}
