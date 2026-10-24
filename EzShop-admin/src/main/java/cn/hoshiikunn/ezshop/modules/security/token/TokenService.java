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

    /**
     * 校验令牌
     *
     * @param token JWT 令牌
     * @return 有效返回 {@code userId}
     * <p>
     * 篡改 / 过期 / 登出 / 无效 返回 {@code null}
     */
    Long verifyToken(String token);
}
