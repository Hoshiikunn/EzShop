package cn.hoshiikunn.ezshop.modules.sys.service.impl;

import cn.hoshiikunn.ezshop.exception.BusinessException;
import cn.hoshiikunn.ezshop.modules.security.token.TokenService;
import cn.hoshiikunn.ezshop.modules.security.user.SecurityUser;
import cn.hoshiikunn.ezshop.modules.sys.dto.SysProfilePasswordDTO;
import cn.hoshiikunn.ezshop.modules.sys.entity.SysUserEntity;
import cn.hoshiikunn.ezshop.modules.sys.service.SysProfileService;
import cn.hoshiikunn.ezshop.modules.sys.service.SysUserRoleService;
import cn.hoshiikunn.ezshop.modules.sys.service.SysUserService;
import cn.hoshiikunn.ezshop.modules.sys.vo.SysProfileVO;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.BCrypt;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static cn.hoshiikunn.ezshop.common.result.AdminResultCode.*;

/**
 * 个人资料业务实现类
 */
@Service
@RequiredArgsConstructor
public class SysProfileServiceImpl implements SysProfileService {

    private final SysUserService userService;
    private final SysUserRoleService userRoleService;
    private final TokenService tokenService;

    @Override
    public SysProfileVO getProfile() {
        SysUserEntity user = userService.getById(SecurityUser.requireUserId());
        if (user == null) {
            throw new BusinessException(USER_NOT_FOUND);
        }

        return BeanUtil.toBean(user, SysProfileVO.class);
    }

    @Override
    public List<String> getRoles() {
        Long userId = SecurityUser.requireUserId();

        return userRoleService.getEnabledRoleNamesByUserId(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changePassword(SysProfilePasswordDTO dto) {
        Long userId = SecurityUser.requireUserId();

        SysUserEntity user = userService.getById(userId);
        if (user == null) {
            throw new BusinessException(USER_NOT_FOUND);
        }

        // 校验原密码
        if (!BCrypt.checkpw(dto.getOldPassword(), user.getPassword())) {
            throw new BusinessException(USER_OLD_PASSWORD_ERROR);
        }

        // 新密码不能与旧密码相同
        if (StrUtil.equals(dto.getOldPassword(), dto.getNewPassword())) {
            throw new BusinessException(USER_PASSWORD_SAME);
        }

        boolean updated = userService.lambdaUpdate()
                .eq(SysUserEntity::getId, userId)
                .set(SysUserEntity::getPassword, BCrypt.hashpw(dto.getNewPassword()))
                .update();
        if (!updated) {
            throw new BusinessException(USER_NOT_FOUND, "密码修改失败，请刷新后重试");
        }

        // 修改成功后删除当前活跃令牌，强制重新登录
        tokenService.removeToken(userId);
    }

}
