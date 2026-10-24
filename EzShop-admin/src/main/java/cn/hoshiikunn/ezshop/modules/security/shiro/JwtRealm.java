package cn.hoshiikunn.ezshop.modules.security.shiro;

import cn.hoshiikunn.ezshop.modules.security.token.TokenService;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

/**
 * 校验核心
 */
@Component
@RequiredArgsConstructor
public class JwtRealm extends AuthorizingRealm {

    private final TokenService tokenService;

    /**
     * 声明该Realm可以校验 JWT令牌
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 获取认证信息
     *
     * @param authenticationToken 身份验证令牌
     * @return 认证信息
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) {
        // 获取并校验令牌，有效返回 userId，无效返回 null
        String jwt = (String) authenticationToken.getPrincipal();
        Long userId = tokenService.verifyToken(jwt);
        if (userId == null) {
            // userId 为 null 抛 shiro 异常 -> onLoginFailure 统一响应
            throw new ExpiredCredentialsException();
        }

        // principal 放 userId，不查库
        return new SimpleAuthenticationInfo(userId, jwt, getName());
    }

    /**
     * 获取授权信息
     *
     * @param principals 主体集合
     * @return 授权信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return new SimpleAuthorizationInfo();
    }
}
