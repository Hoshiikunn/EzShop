package cn.hoshiikunn.ezshop.modules.sys.service;


import cn.hoshiikunn.ezshop.base.BaseService;
import cn.hoshiikunn.ezshop.modules.sys.entity.SysUserRoleEntity;

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
}
