package cn.hoshiikunn.ezshop.modules.sys.service.impl;

import cn.hoshiikunn.ezshop.exception.BusinessException;
import cn.hoshiikunn.ezshop.modules.security.token.TokenService;
import cn.hoshiikunn.ezshop.modules.security.user.SecurityUser;
import cn.hoshiikunn.ezshop.modules.sys.dto.LoginDTO;
import cn.hoshiikunn.ezshop.modules.sys.entity.SysUserEntity;
import cn.hoshiikunn.ezshop.modules.sys.service.SysAuthService;
import cn.hoshiikunn.ezshop.modules.sys.service.SysUserService;
import cn.hoshiikunn.ezshop.modules.sys.vo.LoginTokenVO;
import cn.hutool.crypto.digest.BCrypt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static cn.hoshiikunn.ezshop.result.ResultCode.UNAUTHORIZED;
import static cn.hoshiikunn.ezshop.result.ResultCode.USERNAME_OR_PASSWORD_ERROR;

@Slf4j
@Service
@RequiredArgsConstructor
public class SysAuthServiceImpl implements SysAuthService {

    private final SysUserService userService;
    private final TokenService tokenService;

    @Override
    public LoginTokenVO login(LoginDTO dto) {
        String username = dto.getUsername();
        String password = dto.getPassword();

        // 用户不存在或密码错误 -> 抛异常
        SysUserEntity userEntity = userService.getUserEntityByUsername(username);
        if (userEntity == null || !BCrypt.checkpw(password, userEntity.getPassword())) {
            throw new BusinessException(USERNAME_OR_PASSWORD_ERROR);
        }

        // 签发令牌 存储redis
        return tokenService.issueToken(userEntity.getId());
    }

    @Override
    public void logout() {
        Long userId = SecurityUser.getUserId();
        if (userId == null) {
            log.warn("登出未获取到用户身份");
            throw new BusinessException(UNAUTHORIZED);
        }

        tokenService.removeToken(userId);
    }
}
