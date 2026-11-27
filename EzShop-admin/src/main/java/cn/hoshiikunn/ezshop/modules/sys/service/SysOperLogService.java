package cn.hoshiikunn.ezshop.modules.sys.service;

import cn.hoshiikunn.ezshop.base.BaseService;
import cn.hoshiikunn.ezshop.modules.sys.dto.SysOperLogPageDTO;
import cn.hoshiikunn.ezshop.modules.sys.entity.SysOperLogEntity;
import cn.hoshiikunn.ezshop.modules.sys.vo.SysOperLogPageVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Set;

/**
 * 登录日志业务接口
 */
public interface SysOperLogService extends BaseService<SysOperLogEntity> {

    /**
     * 操作日志分页查询
     *
     * @param dto 操作日志分页条件查询参数
     * @return 操作日志分页数据
     */
    Page<SysOperLogPageVO> pageOperLog(SysOperLogPageDTO dto);

    /**
     * 批量删除操作日志
     *
     * @param operIds 操作日志ID集合
     */
    void batchDeleteOperLogs(Set<Long> operIds);
}
