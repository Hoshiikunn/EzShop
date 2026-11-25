package cn.hoshiikunn.ezshop.modules.sys.dao;

import cn.hoshiikunn.ezshop.modules.sys.entity.SysLoginLogEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 登录日志数据访问层
 */
@Mapper
public interface SysLoginLogDao extends BaseMapper<SysLoginLogEntity> {
}
