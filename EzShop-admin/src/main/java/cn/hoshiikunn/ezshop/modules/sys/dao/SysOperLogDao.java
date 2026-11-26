package cn.hoshiikunn.ezshop.modules.sys.dao;

import cn.hoshiikunn.ezshop.modules.sys.entity.SysOperLogEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 操作日志数据访问层
 */
@Mapper
public interface SysOperLogDao extends BaseMapper<SysOperLogEntity> {
}
