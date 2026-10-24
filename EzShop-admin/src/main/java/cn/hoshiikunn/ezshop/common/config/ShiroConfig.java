package cn.hoshiikunn.ezshop.common.config;

import cn.hoshiikunn.ezshop.modules.security.shiro.JwtFilter;
import cn.hoshiikunn.ezshop.modules.security.shiro.JwtRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.config.ShiroFilterConfiguration;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Shiro配置类
 * <p>
 * 无状态架构：JWT + Redis 白名单，关闭 Shiro 全部 Session 能力
 */
@Configuration
public class ShiroConfig {

    /**
     * 会话管理器
     * <p>
     * JWT 无状态，关闭所有 Session 相关能力
     */
    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        // 关闭 Session 定时过期校验
        sessionManager.setSessionValidationSchedulerEnabled(false);
        // 关闭 URL 拼接 sessionId
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        // 禁用 Session Cookie
        sessionManager.setSessionIdCookieEnabled(false);

        return sessionManager;
    }


    /**
     * 安全管理器
     */
    @Bean("securityManager")
    public DefaultWebSecurityManager securityManager(JwtRealm jwtRealm, DefaultWebSessionManager sessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // JWT Realm
        securityManager.setRealm(jwtRealm);
        // 无状态会话管理器
        securityManager.setSessionManager(sessionManager);
        // 无状态令牌，关闭 rememberMe
        securityManager.setRememberMeManager(null);

        return securityManager;
    }

    /**
     * Shiro 过滤器工厂
     * <p>
     * 注册 JwtFilter 并构建过滤链，yml 白名单 anon 放行，其余请求一律走 jwt 认证
     */
    @Bean("shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(
            SecurityManager securityManager,
            ShiroFilterProperties filterProperties) {
        // 过滤链配置：每个请求只经过一次 Shiro 过滤链 (防止 /error 转发等重复过滤)
        ShiroFilterConfiguration config = new ShiroFilterConfiguration();
        config.setFilterOncePerRequest(true);

        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        shiroFilter.setShiroFilterConfiguration(config);

        // 注册自定义过滤器，起名 jwt
        Map<String, Filter> filters = new HashMap<>();
        filters.put("jwt", new JwtFilter());
        shiroFilter.setFilters(filters);

        // 过滤链：yml 白名单 anon + 兜底 jwt
        shiroFilter.setFilterChainDefinitionMap(buildFilterChain(filterProperties));
        return shiroFilter;
    }

    /**
     * 构建过滤器链
     * <p>
     * yml 配置的白名单按配置顺序走 anon，其余路径兜底走 jwt (未匹配即需认证)
     */
    private Map<String, String> buildFilterChain(ShiroFilterProperties filterProperties) {
        Map<String, String> filterMap = new LinkedHashMap<>();
        List<String> anonPaths = filterProperties.getAnonPaths();
        if (anonPaths != null) {
            for (String path : anonPaths) {
                filterMap.put(path, "anon");
            }
        }
        // 兜底：所有未匹配的接口需要 JWT 认证
        filterMap.put("/**", "jwt");
        return filterMap;
    }
}