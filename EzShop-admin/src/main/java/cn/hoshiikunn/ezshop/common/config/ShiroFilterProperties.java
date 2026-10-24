package cn.hoshiikunn.ezshop.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Shiro 过滤器链配置
 * <p>
 * 白名单路径走 anon，其余路径由 ShiroConfig 代码兜底走 jwt
 */
@Data
@ConfigurationProperties(prefix = "shiro")
public class ShiroFilterProperties {

    /**
     * 免认证白名单路径列表
     */
    private List<String> anonPaths = new ArrayList<>();
}
