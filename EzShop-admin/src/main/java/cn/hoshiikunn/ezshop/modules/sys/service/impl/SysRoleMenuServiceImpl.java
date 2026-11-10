package cn.hoshiikunn.ezshop.modules.sys.service.impl;

import cn.hoshiikunn.ezshop.base.BaseServiceImpl;
import cn.hoshiikunn.ezshop.exception.BusinessException;
import cn.hoshiikunn.ezshop.modules.sys.dao.SysRoleMenuDao;
import cn.hoshiikunn.ezshop.modules.sys.entity.SysMenuEntity;
import cn.hoshiikunn.ezshop.modules.sys.entity.SysRoleMenuEntity;
import cn.hoshiikunn.ezshop.modules.sys.service.SysMenuService;
import cn.hoshiikunn.ezshop.modules.sys.service.SysRoleMenuService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static cn.hoshiikunn.ezshop.common.enums.CommonStatusEnum.ENABLE;
import static cn.hoshiikunn.ezshop.common.result.AdminResultCode.MENU_NOT_FOUND;

/**
 * 角色菜单服务实现类
 */
@Service
@RequiredArgsConstructor
public class SysRoleMenuServiceImpl extends BaseServiceImpl<SysRoleMenuDao, SysRoleMenuEntity> implements SysRoleMenuService {

    private final SysMenuService menuService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(Long roleId, Set<Long> menuIds) {
        // 空集合表示清空该角色的全部菜单绑定
        if (menuIds == null || menuIds.isEmpty()) {
            deleteByRoleId(roleId);
            return;
        }

        // 检查菜单是否可绑定
        List<SysMenuEntity> menus = menuService.lambdaQuery()
                .in(SysMenuEntity::getId, menuIds)
                .eq(SysMenuEntity::getStatus, ENABLE.getValue())
                .list();
        if (menus.size() != menuIds.size()) {
            throw new BusinessException(MENU_NOT_FOUND, "菜单不存在或不可绑定");
        }

        // 保存角色菜单关系
        List<SysRoleMenuEntity> insertList = new ArrayList<>();
        for (Long menuId : menuIds) {
            SysRoleMenuEntity entity = new SysRoleMenuEntity();
            entity.setRoleId(roleId);
            entity.setMenuId(menuId);
            insertList.add(entity);
        }

        // 先删后插
        deleteByRoleId(roleId);
        this.saveBatch(insertList);
    }

    @Override
    public void deleteByRoleId(Long roleId) {
        LambdaQueryWrapper<SysRoleMenuEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRoleMenuEntity::getRoleId, roleId);
        this.remove(wrapper);
    }

    @Override
    public Set<Long> getMenuIdsByRoleId(Long roleId) {
        if (roleId == null) {
            return Collections.emptySet();
        }

        return this.lambdaQuery()
                .eq(SysRoleMenuEntity::getRoleId, roleId)
                .list()
                .stream()
                .map(SysRoleMenuEntity::getMenuId)
                .collect(Collectors.toSet());
    }
}
