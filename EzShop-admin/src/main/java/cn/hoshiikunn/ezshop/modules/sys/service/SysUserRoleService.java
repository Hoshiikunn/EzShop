package cn.hoshiikunn.ezshop.modules.sys.service;


import cn.hoshiikunn.ezshop.base.BaseService;
import cn.hoshiikunn.ezshop.modules.sys.entity.SysRoleEntity;
import cn.hoshiikunn.ezshop.modules.sys.entity.SysUserRoleEntity;

import java.util.List;
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
     * 根据角色ID删除该角色与用户们的绑定
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
     * 根据用户ID删除该用户绑定的角色
     *
     * @param id 用户ID
     */
    void deleteByUserId(Long id);

    /**
     * 获取用户所属的角色列表
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    List<SysRoleEntity> getRolesByUserId(Long userId);
}
