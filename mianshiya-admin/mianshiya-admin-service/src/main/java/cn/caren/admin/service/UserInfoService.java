package cn.caren.admin.service;

import cn.caren.admin.entity.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author caren
 * @data 2022/7/26 03:44
 */
public interface UserInfoService extends IService<UserInfo> {

    /**
     * 根据userId 查询信息
     * @param userId 主键
     * @return UserInfo
     */
    UserInfo selectUserInfoById(Long userId);
}
