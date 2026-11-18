package cn.hoshiikunn.ezshop.modules.sys.dao;

import cn.hoshiikunn.ezshop.modules.sys.entity.SysMenuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Set;

@Mapper
public interface SysMenuDao extends BaseMapper<SysMenuEntity> {

    /**
     * 根据用户ID查询菜单ID集合
     *
     * @param userId 用户ID
     * @return 菜单ID集合
     */
    Set<Long> selectMenuIdsByUserId(Long userId);
}
