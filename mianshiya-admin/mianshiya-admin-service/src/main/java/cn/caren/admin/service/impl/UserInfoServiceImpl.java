package cn.caren.admin.service.impl;

import cn.caren.admin.entity.UserInfo;
import cn.caren.admin.mapper.UserInfoMapper;
import cn.caren.admin.service.UserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author caren
 * @data 2022/7/26 03:45
 */

@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {
    @Override
    public UserInfo selectUserInfoById(Long userId) {
        Optional<UserInfo> userInfoOptional = Optional.ofNullable(this.getById(userId));
        if (userInfoOptional.isPresent()){
            return userInfoOptional.get();
        }
        // TODO: 2022/7/26 custom exception
        throw new RuntimeException("");
    }
}
