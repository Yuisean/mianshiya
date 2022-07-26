package cn.caren.admin.service.impl;

import cn.caren.admin.entity.UserAuth;
import cn.caren.admin.mapper.UserAuthMapper;
import cn.caren.admin.service.UserAuthService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author caren
 */
@Service
public class UserAuthServiceImpl extends ServiceImpl<UserAuthMapper, UserAuth> implements UserAuthService {


    @Override
    public Optional<UserAuth> selectUserByEmail(String email) {
        return this.lambdaQuery().eq(UserAuth::getEmail, email).oneOpt();
    }
}
