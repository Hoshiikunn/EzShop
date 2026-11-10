package cn.hoshiikunn.ezshop.modules.sys.service;

import cn.hoshiikunn.ezshop.base.BaseService;
import cn.hoshiikunn.ezshop.modules.sys.entity.SysRoleMenuEntity;

import java.util.Set;

/**
 * 角色菜单服务层
 */
public interface SysRoleMenuService extends BaseService<SysRoleMenuEntity> {

    /**
     * 保存或更新角色菜单关系
     *
     * @param roleId     角色ID
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
     * 根据角色ID查询已绑定的菜单ID集合
     *
     * @param id 角色ID
     * @return 菜单ID集合
     */
    Set<Long> getMenuIdsByRoleId(Long id);
}
