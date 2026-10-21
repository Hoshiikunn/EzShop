package cn.hoshiikunn.ezshop.modules.security.token;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

/**
 * 令牌配置
 */
@Data
@ConfigurationProperties(prefix = "token")
public class TokenProperties {

    /**
     * 签名密钥
     */
    private String secret;

    /**
     * 令牌有效期 (小时)
     */
    private Duration expire = Duration.ofHours(8);
}
