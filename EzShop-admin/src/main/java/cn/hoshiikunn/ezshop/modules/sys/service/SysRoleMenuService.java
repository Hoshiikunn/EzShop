package cn.hoshiikunn.ezshop.modules.sys.service;

import cn.hoshiikunn.ezshop.base.BaseService;
import cn.hoshiikunn.ezshop.modules.sys.entity.SysMenuEntity;
import cn.hoshiikunn.ezshop.modules.sys.entity.SysRoleMenuEntity;

import java.util.List;
import java.util.Set;

/**
 * 角色菜单服务层
 */
public interface SysRoleMenuService extends BaseService<SysRoleMenuEntity> {

    /**
     * 保存或更新角色菜单关系
     *
     * @param roleId  角色ID
     * @param menuIds 菜单ID列表
     */
    void saveOrUpdate(Long roleId, Set<Long> menuIds);

    /**
     * 根据角色ID删除角色菜单关系
     *
     * @param roleId 角色ID
     */
    void deleteByRoleId(Long roleId);

    /**
     * 获取角色所属的菜单列表
     *
     * @param roleId 角色ID
     * @return 菜单列表
     */
    List<SysMenuEntity> getMenusByRoleId(Long roleId);
}
