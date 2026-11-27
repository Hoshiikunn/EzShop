package cn.hoshiikunn.ezshop.modules.sys.service.impl;

import cn.hoshiikunn.ezshop.exception.BusinessException;
import cn.hoshiikunn.ezshop.modules.security.token.TokenService;
import cn.hoshiikunn.ezshop.modules.security.user.SecurityUser;
import cn.hoshiikunn.ezshop.modules.sys.dto.LoginDTO;
import cn.hoshiikunn.ezshop.modules.sys.entity.SysUserEntity;
import cn.hoshiikunn.ezshop.modules.sys.service.SysAuthService;
import cn.hoshiikunn.ezshop.modules.sys.service.SysLoginLogService;
import cn.hoshiikunn.ezshop.modules.sys.service.SysMenuService;
import cn.hoshiikunn.ezshop.modules.sys.service.SysUserService;
import cn.hoshiikunn.ezshop.modules.sys.vo.LoginTokenVO;
import cn.hoshiikunn.ezshop.modules.sys.vo.SysMenuTreeVO;
import cn.hoshiikunn.ezshop.utils.IpUtils;
import cn.hutool.crypto.digest.BCrypt;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static cn.hoshiikunn.ezshop.common.result.AdminResultCode.USERNAME_OR_PASSWORD_ERROR;
import static cn.hoshiikunn.ezshop.common.result.AdminResultCode.USER_DISABLED;
import static cn.hoshiikunn.ezshop.modules.sys.enums.IsSuperAdminEnum.IS_SUPER_ADMIN;
import static cn.hoshiikunn.ezshop.modules.sys.enums.LoginStatusEnum.FAIL;
import static cn.hoshiikunn.ezshop.modules.sys.enums.LoginStatusEnum.SUCCESS;
import static cn.hoshiikunn.ezshop.modules.sys.enums.SysUserStatusEnum.DISABLE;
import static cn.hoshiikunn.ezshop.result.ResultCode.UNAUTHORIZED;

/**
 * 认证业务实现类
 */
@Service
@RequiredArgsConstructor
public class SysAuthServiceImpl implements SysAuthService {

    private final SysUserService userService;
    private final TokenService tokenService;
    private final SysMenuService menuService;
    private final SysLoginLogService loginLogService;

    @Override
    public LoginTokenVO login(HttpServletRequest request, LoginDTO dto) {
        String username = dto.getUsername();
        String password = dto.getPassword();

        // 用户不存在或密码错误
        SysUserEntity userEntity = userService.getUserEntityByUsername(username);
        if (userEntity == null || !BCrypt.checkpw(password, userEntity.getPassword())) {
            loginLogService.recordLogin(username, request, FAIL, USERNAME_OR_PASSWORD_ERROR.getMsg());
            throw new BusinessException(USERNAME_OR_PASSWORD_ERROR);
        }
        // 状态检查
        if (DISABLE.getValue().equals(userEntity.getStatus())) {
            loginLogService.recordLogin(username, request, FAIL, USER_DISABLED.getMsg());
            throw new BusinessException(USER_DISABLED);
        }

        Long userId = userEntity.getId();
        // 签发令牌 存储redis
        LoginTokenVO token = tokenService.issueToken(userId);
        // 获取登录的客户端IP
        String clientIp = IpUtils.getClientIp(request);
        // 回写登录IP和登录时间
        userService.updateLastLoginInfo(clientIp, userId);
        // 写入日志
        loginLogService.recordLogin(userEntity.getUsername(),
                request, SUCCESS, "登录成功");

        return token;
    }

    @Override
    public void logout() {
        tokenService.removeToken(SecurityUser.requireUserId());
    }

    @Override
    public List<SysMenuTreeVO> getNavMenus() {
        Long userId = SecurityUser.requireUserId();
        SysUserEntity user = userService.getById(userId);
        if (user == null || DISABLE.getValue().equals(user.getStatus())) {
            throw new BusinessException(UNAUTHORIZED);
        }

        boolean superAdmin = IS_SUPER_ADMIN.getValue().equals(user.getIsSuperAdmin());

        return menuService.getNavMenus(userId, superAdmin);
    }
}
