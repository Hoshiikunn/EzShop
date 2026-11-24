package cn.hoshiikunn.ezshop.modules.sys.service.impl;

import cn.hoshiikunn.ezshop.exception.BusinessException;
import cn.hoshiikunn.ezshop.modules.security.token.TokenService;
import cn.hoshiikunn.ezshop.modules.security.user.SecurityUser;
import cn.hoshiikunn.ezshop.modules.sys.dto.SysProfilePasswordDTO;
import cn.hoshiikunn.ezshop.modules.sys.dto.SysProfileUsernameDTO;
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

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static cn.hoshiikunn.ezshop.common.result.AdminResultCode.*;
import static cn.hoshiikunn.ezshop.modules.sys.enums.IsSuperAdminEnum.IS_SUPER_ADMIN;

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

        SysUserEntity updateEntity = new SysUserEntity();
        updateEntity.setId(user.getId());
        updateEntity.setPassword(BCrypt.hashpw(dto.getNewPassword()));
        updateEntity.setVersion(user.getVersion());

        boolean updated = userService.updateById(updateEntity);
        if (!updated) {
            throw new BusinessException(USER_NOT_FOUND, "密码修改失败，请刷新后重试");
        }

        // 修改成功后删除当前活跃令牌，强制重新登录
        tokenService.removeToken(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changeUsername(SysProfileUsernameDTO dto) {
        Long userId = SecurityUser.requireUserId();

        SysUserEntity user = userService.getById(userId);
        if (user == null) {
            throw new BusinessException(USER_NOT_FOUND);
        }

        // 用户名未变化时直接返回
        if (StrUtil.equals(user.getUsername(), dto.getNewUsername())) {
            return;
        }

        // 新用户名查重，排除自己
        boolean exists = userService.lambdaQuery()
                .eq(SysUserEntity::getUsername, dto.getNewUsername())
                .ne(SysUserEntity::getId, userId)
                .exists();
        if (exists) {
            throw new BusinessException(USER_USERNAME_EXISTS);
        }

        // 只更新 username，并携带旧 version，触发乐观锁和审计字段填充
        SysUserEntity updateEntity = new SysUserEntity();
        updateEntity.setId(user.getId());
        updateEntity.setUsername(dto.getNewUsername());
        updateEntity.setVersion(user.getVersion());

        boolean updated = userService.updateById(updateEntity);
        if (!updated) {
            throw new BusinessException(USER_NOT_FOUND, "用户名修改失败，请刷新后重试");
        }
    }

    @Override
    public List<String> getPermissions() {
        // 取当前登录的用户实体
        Long userId = SecurityUser.requireUserId();
        SysUserEntity user = userService.getById(userId);
        if (user == null) {
            throw new BusinessException(USER_NOT_FOUND);
        }

        // 超级管理员返回全量权限标识
        if (IS_SUPER_ADMIN.getValue().equals(user.getIsSuperAdmin())) {
            return Collections.singletonList("*");
        }

        // 普通用户返回启用角色绑定的权限标识，去空并排序
        return userService.getPermsByUserId(userId).stream()
                .filter(StrUtil::isNotBlank)
                .sorted()
                .collect(Collectors.toList());
    }
}
