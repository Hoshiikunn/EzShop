package cn.hoshiikunn.ezshop.modules.sys.dao;

import cn.hoshiikunn.ezshop.modules.sys.entity.SysUserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * 用户数据访问层
 */
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

    /**
     * 批量写入用户删除墓碑并标记逻辑删除
     *
     * @param tombstones 用户墓碑参数，必须包含用户ID
     * @return 实际更新数量
     */
    int updateBatchTombstones(@Param("tombstones") List<SysUserEntity> tombstones,
                              @Param("updater") Long updater);
}
