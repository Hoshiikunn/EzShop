package cn.hoshiikunn.ezshop.common.config;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Shiro配置类 (当前最小可用)
 */
@Configuration
public class ShiroConfig {

    @Bean
    public DefaultWebSecurityManager securityManager() {

        return new DefaultWebSecurityManager();
    }

    @Bean
    public Realm realm() {
        // 占位 Realm
        return new AuthorizingRealm() {
            @Override
            protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
                return null;
            }

            @Override
            protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
                return null;
            }
        };
    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        // 当前全放行
        chainDefinition.addPathDefinition("/**", "anon");
        return chainDefinition;
    }
}