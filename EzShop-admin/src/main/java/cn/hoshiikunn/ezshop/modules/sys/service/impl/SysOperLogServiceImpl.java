package cn.hoshiikunn.ezshop.modules.sys.service.impl;

import cn.hoshiikunn.ezshop.base.BaseServiceImpl;
import cn.hoshiikunn.ezshop.modules.sys.dao.SysOperLogDao;
import cn.hoshiikunn.ezshop.modules.sys.dto.SysOperLogPageDTO;
import cn.hoshiikunn.ezshop.modules.sys.entity.SysOperLogEntity;
import cn.hoshiikunn.ezshop.modules.sys.service.SysOperLogService;
import cn.hoshiikunn.ezshop.modules.sys.vo.SysOperLogPageVO;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 操作日志业务实现类
 */
@Service
@RequiredArgsConstructor
public class SysOperLogServiceImpl extends BaseServiceImpl<SysOperLogDao, SysOperLogEntity>
        implements SysOperLogService {

    @Override
    public Page<SysOperLogPageVO> pageOperLog(SysOperLogPageDTO dto) {
        // 构建分页实体
        Page<SysOperLogEntity> page = new Page<>(dto.getCurrent(), dto.getSize());

        // 分页查询并转换 VO
        Page<SysOperLogEntity> result = this.page(page, this.lambdaQuery()
                .like(StrUtil.isNotBlank(dto.getModule()), SysOperLogEntity::getModule, dto.getModule())
                .like(StrUtil.isNotBlank(dto.getOperation()), SysOperLogEntity::getOperation, dto.getOperation())
                .like(StrUtil.isNotBlank(dto.getUsername()), SysOperLogEntity::getUsername, dto.getUsername())
                .like(StrUtil.isNotBlank(dto.getIp()), SysOperLogEntity::getIp, dto.getIp())
                .eq(dto.getUserId() != null, SysOperLogEntity::getUserId, dto.getUserId())
                .eq(dto.getStatus() != null, SysOperLogEntity::getStatus, dto.getStatus())
                .ge(dto.getCreateTimeBegin() != null, SysOperLogEntity::getCreateTime, dto.getCreateTimeBegin())
                .le(dto.getCreateTimeEnd() != null, SysOperLogEntity::getCreateTime, dto.getCreateTimeEnd())
                .orderByDesc(SysOperLogEntity::getCreateTime)
                .orderByDesc(SysOperLogEntity::getId)
                .getWrapper());
        return (Page<SysOperLogPageVO>) result.convert(log -> BeanUtil.toBean(log, SysOperLogPageVO.class));
    }
}
