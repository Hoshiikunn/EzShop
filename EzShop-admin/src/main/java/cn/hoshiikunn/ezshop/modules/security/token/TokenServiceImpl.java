package cn.hoshiikunn.ezshop.modules.security.token;

import cn.hoshiikunn.ezshop.modules.sys.vo.LoginTokenVO;
import cn.hoshiikunn.ezshop.redis.RedisUtils;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;

/**
 * 令牌业务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenProperties properties;
    private final RedisUtils redisUtils;

    /**
     * 令牌键前缀
     */
    private static final String TOKEN_KEY_PREFIX = "auth:admin:token:";
    /**
     * JWT 用户ID声明键
     */
    private static final String CLAIM_UID = "uid";
    /**
     * JWT 令牌唯一ID声明键
     */
    private static final String CLAIM_JTI = "jti";

    @Override
    public LoginTokenVO issueToken(Long userId) {
        String jti = IdUtil.fastSimpleUUID();
        Duration ttl = properties.getExpire();
        Date expiresAt = new Date(System.currentTimeMillis() + ttl.toMillis());

        // 生成令牌
        String token = JWT.create()
                .setPayload(CLAIM_UID, userId)
                .setJWTId(jti)
                .setKey(secretBytes())
                .setExpiresAt(expiresAt)
                .sign();

        // 写入缓存
        redisUtils.setString(TOKEN_KEY_PREFIX + userId, jti, properties.getExpire());

        // 返回令牌
        LoginTokenVO vo = new LoginTokenVO();
        vo.setToken(token);
        vo.setExpiresAt(expiresAt);
        return vo;
    }

    @Override
    public Long verifyToken(String token) {
        try {
            // 验签 + 过期校验
            JWT jwt = JWT.of(token).setKey(secretBytes());
            if (!jwt.validate(0)) {
                log.debug("令牌验签或时间校验失败");
                return null;
            }

            // 读取 uid 与 jti 声明
            Long userId = readUserId(jwt.getPayload(CLAIM_UID));
            String jti = readJti(jwt.getPayload(CLAIM_JTI));

            // 身份声明必须完整
            if (userId == null || StrUtil.isBlank(jti)) {
                log.debug("令牌缺少有效身份声明");
                return null;
            }

            // 当前活跃令牌校验，被互踢 / 已登出 / Redis 已过期都会失败
            String activeJti = redisUtils.getString(TOKEN_KEY_PREFIX + userId);
            if (activeJti == null || !activeJti.equals(jti)) {
                log.debug("令牌不在活跃白名单 | userId={}", userId);
                return null;
            }

            // 校验通过返回用户ID
            return userId;
        } catch (Exception e) {
            log.debug("令牌校验失败 | reason={}", e.getMessage());
            return null;
        }
    }

    @Override
    public void removeToken(Long userId) {
        redisUtils.deleteString(TOKEN_KEY_PREFIX + userId);
    }

    /**
     * 密钥转换字节数组
     *
     * @return 字节数组
     */
    private byte[] secretBytes() {
        return properties.getSecret().getBytes(StandardCharsets.UTF_8);
    }

    /**
     * 读取 JWT 令牌唯一ID
     *
     * @param payload jti 声明原始值
     * @return jti 声明值 <p>
     * 非字符串时返回 {@code null}
     */
    private String readJti(Object payload) {
        return payload instanceof String ? (String) payload : null;
    }

    /**
     * 读取 JWT 用户ID
     * <p>
     * 支持 Number 与字符串类型的 uid 声明
     *
     * @param payload uid 声明原始值
     * @return 用户ID <p>
     * 声明缺失、类型不支持或格式非法时返回 {@code null}
     */
    private Long readUserId(Object payload) {
        if (payload instanceof Number) {
            return ((Number) payload).longValue();
        }

        if (payload instanceof String) {
            try {
                return Long.parseLong((String) payload);
            } catch (NumberFormatException e) {
                return null;
            }
        }

        return null;
    }
}
