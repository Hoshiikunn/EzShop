package cn.hoshiikunn.ezshop.modules.sys.service.impl;

import cn.hoshiikunn.ezshop.base.BaseServiceImpl;
import cn.hoshiikunn.ezshop.modules.sys.dao.SysLoginLogDao;
import cn.hoshiikunn.ezshop.modules.sys.dto.SysLoginLogPageDTO;
import cn.hoshiikunn.ezshop.modules.sys.entity.SysLoginLogEntity;
import cn.hoshiikunn.ezshop.modules.sys.enums.LoginStatusEnum;
import cn.hoshiikunn.ezshop.modules.sys.service.SysLoginLogService;
import cn.hoshiikunn.ezshop.modules.sys.vo.SysLoginLogPageVO;
import cn.hoshiikunn.ezshop.utils.AddressUtils;
import cn.hoshiikunn.ezshop.utils.IpUtils;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录日志业务实现类
 */
@Service
@RequiredArgsConstructor
public class SysLoginLogServiceImpl extends BaseServiceImpl<SysLoginLogDao, SysLoginLogEntity>
        implements SysLoginLogService {

    /**
     * UA 解析字段的列宽上限
     */
    private static final int MAX_OS_BROWSER_LENGTH = 50;
    /**
     * UA 解析失败兜底文案
     */
    private static final String UNKNOWN = "未知";

    // TODO: 当前为同步落库，提升性能建议使用 MQ
    @Override
    public void recordLogin(String username, HttpServletRequest request,
                            LoginStatusEnum status, String message) {
        // 提取客户端 IP 与浏览器标识
        String ip = IpUtils.getClientIp(request);
        String userAgent = request.getHeader("User-Agent");

        SysLoginLogEntity entity = new SysLoginLogEntity();
        entity.setUsername(username);
        entity.setIp(ip);
        entity.setLoginLocation(AddressUtils.getRealAddress(ip));
        entity.setOs(parseOs(userAgent));
        entity.setBrowser(parseBrowser(userAgent));
        entity.setStatus(status.getValue());
        entity.setMessage(message);

        this.save(entity);
    }

    @Override
    public Page<SysLoginLogPageVO> pageLoginLog(SysLoginLogPageDTO dto) {
        // 构建分页实体
        Page<SysLoginLogEntity> page = new Page<>(dto.getCurrent(), dto.getSize());

        // 分页查询并转换 VO
        Page<SysLoginLogEntity> result = this.page(page, this.lambdaQuery()
                .like(StrUtil.isNotBlank(dto.getUsername()), SysLoginLogEntity::getUsername, dto.getUsername())
                .like(StrUtil.isNotBlank(dto.getIp()), SysLoginLogEntity::getIp, dto.getIp())
                .eq(dto.getStatus() != null, SysLoginLogEntity::getStatus, dto.getStatus())
                .ge(dto.getCreateTimeBegin() != null, SysLoginLogEntity::getCreateTime, dto.getCreateTimeBegin())
                .le(dto.getCreateTimeEnd() != null, SysLoginLogEntity::getCreateTime, dto.getCreateTimeEnd())
                .orderByDesc(SysLoginLogEntity::getCreateTime)
                .orderByDesc(SysLoginLogEntity::getId)
                .getWrapper());

        return (Page<SysLoginLogPageVO>) result.convert(log -> BeanUtil.toBean(log, SysLoginLogPageVO.class));
    }

    /**
     * 解析操作系统
     */
    private String parseOs(String userAgent) {
        UserAgent ua = UserAgentUtil.parse(userAgent);
        return ua == null ? UNKNOWN : StrUtil.subPre(ua.getOs().getName(), MAX_OS_BROWSER_LENGTH);
    }

    /**
     * 解析浏览器
     */
    private String parseBrowser(String userAgent) {
        UserAgent ua = UserAgentUtil.parse(userAgent);
        return ua == null ? UNKNOWN : StrUtil.subPre(ua.getBrowser().getName(), MAX_OS_BROWSER_LENGTH);
    }
}
