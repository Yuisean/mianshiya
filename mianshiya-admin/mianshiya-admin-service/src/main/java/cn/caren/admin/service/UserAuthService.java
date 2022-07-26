package cn.caren.admin.service;

import cn.caren.admin.entity.UserAuth;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Optional;

/**
 * @author caren
 */
public interface UserAuthService extends IService<UserAuth> {

    /**
     * 根据邮箱查询用户信息
     * @param email 邮箱
     * @return Optional<UserAuth>
     */
    Optional<UserAuth> selectUserByEmail(String email);
}
