package cn.hoshiikunn.ezshop.modules.security.token;

import cn.hoshiikunn.ezshop.modules.sys.vo.LoginTokenVO;
import cn.hoshiikunn.ezshop.redis.RedisUtils;
import cn.hutool.core.util.IdUtil;
import cn.hutool.jwt.JWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    /**
     * 令牌键前缀
     */
    private static final String TOKEN_KEY_PREFIX = "auth:admin:token:";
    /**
     * 用户键前缀
     */
    private static final String USER_KEY_PREFIX = "auth:admin:user:";

    private final TokenProperties properties;
    private final RedisUtils redisUtils;

    @Override
    public LoginTokenVO issueToken(Long userId) {
        String jti = IdUtil.fastSimpleUUID();
        Duration ttl = properties.getExpire();
        Date expiresAt = new Date(System.currentTimeMillis() + ttl.toMillis());

        // 生成令牌
        String token = JWT.create()
                .setPayload("uid", userId)
                .setJWTId(jti)
                .setKey(secretBytes())
                .setExpiresAt(expiresAt)
                .sign();

        // 互踢
        String oldJti = (String) redisUtils.get(USER_KEY_PREFIX + userId);
        if (oldJti != null) {
            redisUtils.delete(TOKEN_KEY_PREFIX + oldJti);
        }

        redisUtils.set(TOKEN_KEY_PREFIX + jti, String.valueOf(userId), ttl);
        redisUtils.set(USER_KEY_PREFIX + userId, jti, ttl);

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

            // 取 jti 查白名单，被互踢 / 已登出 / Redis 已过期的令牌在此失效
            String jti = (String) jwt.getPayload("jti");
            String userId = (String) redisUtils.get(TOKEN_KEY_PREFIX + jti);
            if (userId == null) {
                log.debug("令牌 Redis 白名单无记录 | jti = {}", jti);
                return null;
            }

            return Long.valueOf(userId);
        } catch (Exception e) {
            log.debug("格式非法 / 验签过程异常：{}", e.getMessage());

            return null;
        }
    }

    /**
     * 密钥转换字节数组
     *
     * @return 字节数组
     */
    private byte[] secretBytes() {
        return properties.getSecret().getBytes(StandardCharsets.UTF_8);
    }
}
