package cn.caren.auth.service;

import cn.caren.admin.dto.UserInfoDto;
import cn.caren.admin.entity.Role;
import cn.caren.admin.entity.UserAuth;
import cn.caren.admin.entity.UserInfo;
import cn.caren.admin.manager.UserInfoManager;
import cn.caren.admin.service.RoleService;
import cn.caren.admin.service.UserAuthService;
import cn.caren.admin.service.UserInfoService;
import cn.hutool.core.bean.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service("detailsServiceImpl")
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {



    @Resource
    UserAuthService userAuthService;

    @Resource
    UserInfoService userInfoService;

    @Resource
    RoleService roleService;


    /**
     * 登录的主要逻辑
     * @param username the username identifying the user whose data is required.
     * @return UserDetails
     * @throws UsernameNotFoundException 用户或密码错误
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfoDto userInfoDto;
        userInfoDto = UserInfoManager.getUserInfoAll(username);
        // 缓存过期
        if (BeanUtil.isEmpty(userInfoDto)) {
            log.info("redis userinfoDto expire,email: [{}]",username);
            UserAuth userAuth = getAndCheckAuth(username);
            // 查询 userInfo
            UserInfo userInfo = userInfoService.selectUserInfoById(userAuth.getUserId());
            userInfoDto = BeanUtil.copyProperties(userInfo,UserInfoDto.class);
            List<Role> roles = roleService.getRolesByUserId(userInfoDto.getId());
            userInfoDto.setRoles(roles);
            userInfoDto.setAuthId(userAuth.getId());
            userInfoDto.setEmail(userAuth.getEmail());
            userInfoDto.setEnabled(userAuth.getEnabled());
            userInfoDto.setPassword(userAuth.getPassword());
            UserInfoManager.setUserInfo(username,userInfoDto);
        }
        log.info("query param:[{}],return:[{}]",username,userInfoDto);
        return userInfoDto;
    }

    /**
     * 获取用户并检查用户是否可用
     * @param email 邮箱
     * @return UserAuth
     */
    private UserAuth getAndCheckAuth(String email) {
        Optional<UserAuth> authOptional = userAuthService.selectUserByEmail(email);
        // 账号不存在
        if (!authOptional.isPresent()) {
            throw new UsernameNotFoundException("用户名或密码错误！");
        }
        UserAuth userAuth = authOptional.get();
        // 账号被启用
        if (!userAuth.getEnabled()) {
            // TODO: 2022/7/26 global exception
        }
        return userAuth;
    }
}
