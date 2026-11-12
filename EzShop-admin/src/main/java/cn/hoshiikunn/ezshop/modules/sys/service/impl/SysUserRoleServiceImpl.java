package cn.hoshiikunn.ezshop.modules.sys.service.impl;

import cn.hoshiikunn.ezshop.base.BaseServiceImpl;
import cn.hoshiikunn.ezshop.modules.sys.dao.SysUserRoleDao;
import cn.hoshiikunn.ezshop.modules.sys.entity.SysUserRoleEntity;
import cn.hoshiikunn.ezshop.modules.sys.service.SysUserRoleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;

/**
 * 用户角色服务实现类
 */
@Service
public class SysUserRoleServiceImpl extends BaseServiceImpl<SysUserRoleDao, SysUserRoleEntity> implements SysUserRoleService {

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
}
