package cn.hoshiikunn.ezshop.modules.sys.service.impl;

import cn.hoshiikunn.ezshop.base.BaseServiceImpl;
import cn.hoshiikunn.ezshop.exception.BusinessException;
import cn.hoshiikunn.ezshop.modules.sys.dao.SysRoleDao;
import cn.hoshiikunn.ezshop.modules.sys.dao.SysUserRoleDao;
import cn.hoshiikunn.ezshop.modules.sys.entity.SysRoleEntity;
import cn.hoshiikunn.ezshop.modules.sys.entity.SysUserRoleEntity;
import cn.hoshiikunn.ezshop.modules.sys.service.SysRoleService;
import cn.hoshiikunn.ezshop.modules.sys.service.SysUserRoleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static cn.hoshiikunn.ezshop.common.enums.CommonStatusEnum.ENABLE;
import static cn.hoshiikunn.ezshop.common.result.AdminResultCode.ROLE_ID_REQUIRED;
import static cn.hoshiikunn.ezshop.common.result.AdminResultCode.ROLE_NOT_FOUND;

/**
 * 用户角色服务实现类
 */
@Service
@RequiredArgsConstructor
public class SysUserRoleServiceImpl extends BaseServiceImpl<SysUserRoleDao, SysUserRoleEntity> implements SysUserRoleService {

    private final SysRoleDao roleDao;

    @Override
    public long countByRoleId(Long roleId) {
        if (roleId == null) {
            return 0;
        }

        return this.lambdaQuery()
                .eq(SysUserRoleEntity::getRoleId, roleId)
                .count();
    }

    @Override
    public void deleteByRoleId(Long roleId) {
        if (roleId == null) {
            return;
        }

        this.remove(new LambdaQueryWrapper<SysUserRoleEntity>()
                .eq(SysUserRoleEntity::getRoleId, roleId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(Long userId, Set<Long> roleIds) {
        // 空集合表示清空该用户的全部角色绑定
        if (roleIds == null || roleIds.isEmpty()) {
            deleteByUserId(userId);
            return;
        }

        // 防止 roleIds 中包含 null
        if (roleIds.stream().anyMatch(Objects::isNull)) {
            throw new BusinessException(ROLE_ID_REQUIRED, "角色ID不能为空");
        }

        // 检查角色是否可绑定
        List<SysRoleEntity> roles = roleDao.selectList(new LambdaQueryWrapper<SysRoleEntity>()
                .in(SysRoleEntity::getId, roleIds)
                .eq(SysRoleEntity::getStatus, ENABLE.getValue()));
        if (roles.size() != roleIds.size()) {
            throw new BusinessException(ROLE_NOT_FOUND, "角色不存在或不可绑定");
        }

        // 保存用户角色关系
        List<SysUserRoleEntity> userRoles = roleIds.stream()
                .map(roleId -> {
                    SysUserRoleEntity entity = new SysUserRoleEntity();
                    entity.setUserId(userId);
                    entity.setRoleId(roleId);
                    return entity;
                })
                .collect(Collectors.toList());

        // 先删后插
        deleteByUserId(userId);
        this.saveBatch(userRoles);
    }

    @Override
    public void deleteByUserId(Long id) {
        if (id == null) {
            return;
        }

        this.remove(new LambdaQueryWrapper<SysUserRoleEntity>()
                .eq(SysUserRoleEntity::getUserId, id));
    }

    @Override
    public List<SysRoleEntity> getRolesByUserId(Long userId) {
        if (userId == null) {
            return Collections.emptyList();
        }
        return baseMapper.selectRolesByUserId(userId);
    }
}
