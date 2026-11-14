package cn.hoshiikunn.ezshop.modules.sys.dao;

import cn.hoshiikunn.ezshop.modules.sys.entity.SysMenuEntity;
import cn.hoshiikunn.ezshop.modules.sys.entity.SysRoleMenuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleMenuDao extends BaseMapper<SysRoleMenuEntity> {
    
    /**
     * 查询角色所属的菜单列表
     *
     * @param roleId 角色ID
     * @return 菜单列表
     */
    List<SysMenuEntity> selectMenusByRoleId(Long roleId);
}
