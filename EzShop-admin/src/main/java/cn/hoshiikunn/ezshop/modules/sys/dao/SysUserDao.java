package cn.hoshiikunn.ezshop.modules.sys.dao;

import cn.hoshiikunn.ezshop.modules.sys.entity.SysUserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Set;

@Mapper
public interface SysUserDao extends BaseMapper<SysUserEntity> {

    /**
     * 查询用户持有的角色键集合
     */
    Set<String> selectRoleKeysByUserId(Long userId);

    /**
     * 查询用户持有的权限标识集合
     */
    Set<String> selectPermsByUserId(Long userId);
}
