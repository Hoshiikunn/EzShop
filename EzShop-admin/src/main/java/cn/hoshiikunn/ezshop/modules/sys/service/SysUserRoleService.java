package cn.hoshiikunn.ezshop.modules.sys.service;


import cn.hoshiikunn.ezshop.base.BaseService;
import cn.hoshiikunn.ezshop.modules.sys.entity.SysUserRoleEntity;

import java.util.Set;

/**
 * 用户角色服务层
 */
public interface SysUserRoleService extends BaseService<SysUserRoleEntity> {

    /**
     * 查询绑定该角色的用户数量
     *
     * @param id 角色ID
     * @return 绑定的用户数量
     */
    long countByRoleId(Long id);

    /**
     * 根据角色ID删除与用户的绑定
     *
     * @param id 角色ID
     */
    void deleteByRoleId(Long id);

    /**
     * 保存或更新用户角色关系
     *
     * @param userId  用户ID
     * @param roleIds 角色ID列表
     */
    void saveOrUpdate(Long userId, Set<Long> roleIds);

    /**
     * 根据用户ID删除与角色的绑定
     *
     * @param id 用户ID
     */
    void deleteByUserId(Long id);

    /**
     * 根据用户ID获取绑定的角色列表
     *
     * @param id 用户ID
     * @return 角色ID列表
     */
    Set<Long> getRoleIdsByUserId(Long id);
}
