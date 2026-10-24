package cn.hoshiikunn.ezshop.modules.security.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * JWT 认证令牌
 * <p>
 * Shiro 自定义令牌载体
 */
public class JwtToken implements AuthenticationToken {

    /**
     * JWT 令牌字符串
     */
    private final String token;

    public JwtToken(String token) {
        this.token = token;
    }

    /**
     * 身份标识
     */
    @Override
    public Object getPrincipal() {
        return token;
    }

    /**
     * 凭证
     */
    @Override
    public Object getCredentials() {
        return token;
    }
}
