package cn.caren.auth.service;

import cn.caren.admin.manager.UserInfoManager;
import cn.caren.auth.config.TokenProvider;
import cn.caren.auth.manager.AuthCacheManager;
import cn.caren.auth.utils.AuthUtil;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Slf4j
@Component
public class AuthService {

    /**
     * 校验验证码和防止表单重复提交
     *
     * @param code 验证码
     * @param uuid 登录行为唯一标识
     */
    public void checkLoginCode(String code, String uuid) {
        // 防止重复提交
        AuthCacheManager.blockInputRepeat(uuid);
        // 检查code
        AuthCacheManager.checkLoginCode(code, uuid);
        //删除code
        AuthCacheManager.deleteLoginCode(uuid);
    }

    /**
     * 发送验证码
     *
     * @return HashMap<String, String>
     */
    public HashMap<String, String> sendLoginCode() {
        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(200, 100, 4, 4);
        String code = captcha.getCode();
        String uuid = IdUtil.simpleUUID();
        log.info("uuid：[{}],code：[{}]", uuid, code);
        AuthCacheManager.insertLoginCode(code, uuid);
        String imageBase64 = captcha.getImageBase64();
        HashMap<String, String> map = new HashMap<>(2);
        map.put("uuid", uuid);
        map.put("imgCode", imageBase64);
        return map;
    }

    /**
     * 退出登录
     * 此处不需要校验token，{@link cn.caren.auth.filter.TokenFilter}已经校验过了
     */
    public void logout(String token) {
        // 删除缓存中的token
        TokenProvider.deleteToken(token);
        // 删除在线状态
        UserInfoManager.delUserInfo(AuthUtil.getName());
    }
}
