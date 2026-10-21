package cn.hoshiikunn.ezshop.modules.sys.service;

import cn.hoshiikunn.ezshop.base.BaseService;
import cn.hoshiikunn.ezshop.modules.sys.entity.SysUserEntity;

/**
 * 系统用户服务层
 */
public interface SysUserService extends BaseService<SysUserEntity> {
    /**
     * 根据用户名获取用户实体
     *
     * @param username 用户名
     * @return 系统用户实体
     */
    SysUserEntity getUserEntityByUsername(String username);
}
