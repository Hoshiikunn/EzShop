package cn.hoshiikunn.ezshop.modules.security.shiro;

import cn.hoshiikunn.ezshop.modules.security.token.TokenService;
import cn.hoshiikunn.ezshop.modules.sys.entity.SysUserEntity;
import cn.hoshiikunn.ezshop.modules.sys.service.SysUserService;
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

import java.util.HashSet;
import java.util.Set;

import static cn.hoshiikunn.ezshop.modules.sys.enums.IsSuperAdminEnum.IS_SUPER_ADMIN;
import static cn.hoshiikunn.ezshop.modules.sys.enums.SysUserStatusEnum.ENABLE;

/**
 * 校验核心
 */
@Component
@RequiredArgsConstructor
public class JwtRealm extends AuthorizingRealm {

    private final TokenService tokenService;
    private final SysUserService userService;

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
        if (userId == null || !isActiveUser(userService.getById(userId))) {
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
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        // 获取主身份
        Long userId = (Long) principals.getPrimaryPrincipal();
        // TODO: 每次请求都会查 SQL 获取用户信息，可将 isSuperAdmin 字段放入 JWT 中以省略该 SQL
        SysUserEntity user = userService.getById(userId);
        if (!isActiveUser(user)) {
            // 不授予角色和权限
            return info;
        }

        // 装载角色
        Set<String> roleKeys = userService.getRoleKeysByUserId(userId);
        info.setRoles(roleKeys);

        // 若是超级管理员，就赋予全部权限
        if (user.getIsSuperAdmin().equals(IS_SUPER_ADMIN.getValue())) {
            info.addStringPermission("*");
            return info;
        }

        // 装载权限标识
        info.setStringPermissions(new HashSet<>(userService.getPermsByUserId(userId)));
        return info;
    }

    /**
     * 是否为活跃用户
     *
     * @param user 用户实体
     * @return 用户存在且为启用状态 -> {@code true} <p>
     * 否则 -> {@code false}
     */
    private boolean isActiveUser(SysUserEntity user) {
        return user != null
                && ENABLE.getValue().equals(user.getStatus());
    }
}
