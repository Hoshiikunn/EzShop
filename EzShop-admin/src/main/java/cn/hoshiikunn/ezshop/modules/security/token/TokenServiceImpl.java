package cn.hoshiikunn.ezshop.modules.security.token;

import cn.hoshiikunn.ezshop.modules.sys.vo.LoginTokenVO;
import cn.hoshiikunn.ezshop.redis.RedisUtils;
import cn.hutool.core.util.IdUtil;
import cn.hutool.jwt.JWT;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;

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

    /**
     * 密钥转换字节数组
     *
     * @return 字节数组
     */
    private byte[] secretBytes() {
        return properties.getSecret().getBytes(StandardCharsets.UTF_8);
    }
}
