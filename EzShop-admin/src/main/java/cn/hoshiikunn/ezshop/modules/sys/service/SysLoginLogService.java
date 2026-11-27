package cn.hoshiikunn.ezshop.modules.sys.service;

import cn.hoshiikunn.ezshop.base.BaseService;
import cn.hoshiikunn.ezshop.modules.sys.dto.SysLoginLogPageDTO;
import cn.hoshiikunn.ezshop.modules.sys.entity.SysLoginLogEntity;
import cn.hoshiikunn.ezshop.modules.sys.enums.LoginStatusEnum;
import cn.hoshiikunn.ezshop.modules.sys.vo.SysLoginLogPageVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * 登录日志业务接口
 */
public interface SysLoginLogService extends BaseService<SysLoginLogEntity> {

    /**
     * 写入登录日志
     *
     * @param username 尝试登录的用户名
     * @param request  当前请求
     * @param status   登录状态
     * @param message  结果描述
     */
    void recordLogin(String username, HttpServletRequest request,
                     LoginStatusEnum status, String message);

    /**
     * 分页条件查询登录日志
     *
     * @param dto 分页条件查询参数
     * @return 登录日志分页数据
     */
    Page<SysLoginLogPageVO> pageLoginLog(SysLoginLogPageDTO dto);

    /**
     * 批量删除登录日志
     *
     * @param logIds 登录日志ID集合
     */
    void batchDeleteLoginLogs(Set<Long> logIds);
}
