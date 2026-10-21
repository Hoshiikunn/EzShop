package cn.hoshiikunn.ezshop.modules.sys.service;

import cn.hoshiikunn.ezshop.modules.sys.dto.LoginDTO;
import cn.hoshiikunn.ezshop.modules.sys.vo.LoginTokenVO;

/**
 * 系统认证服务层
 */
public interface SysAuthService {
    /**
     * 登录
     *
     * @param dto 账号密码登录参数
     * @return 登录令牌
     */
    LoginTokenVO login(LoginDTO dto);
}
