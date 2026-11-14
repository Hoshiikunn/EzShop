package cn.hoshiikunn.ezshop.modules.sys.dao;

import cn.hoshiikunn.ezshop.modules.sys.entity.SysRoleEntity;
import cn.hoshiikunn.ezshop.modules.sys.entity.SysUserRoleEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysUserRoleDao extends BaseMapper<SysUserRoleEntity> {

    /**
     * 查询用户所属的角色列表
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    List<SysRoleEntity> selectRolesByUserId(Long userId);
}
