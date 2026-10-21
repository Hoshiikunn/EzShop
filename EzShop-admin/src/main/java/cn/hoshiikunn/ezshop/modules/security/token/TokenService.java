package cn.hoshiikunn.ezshop.modules.security.token;

import cn.hoshiikunn.ezshop.modules.sys.vo.LoginTokenVO;

/**
 * 令牌服务层
 */
public interface TokenService {

    /**
     * 签发令牌
     * <p>
     * 签发JWT令牌并存储redis
     *
     * @param userId 用户ID
     * @return 登录令牌
     */
    LoginTokenVO issueToken(Long userId);
}
