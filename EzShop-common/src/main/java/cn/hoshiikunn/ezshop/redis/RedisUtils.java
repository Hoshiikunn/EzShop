package cn.hoshiikunn.ezshop.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RedisUtils {

    private final RedisTemplate<String, Object> redisTemplate;
    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 设置缓存
     *
     * @param key     缓存键
     * @param value   缓存值
     * @param timeout 过期时长 (不可为 null)
     */
    public void set(String key, Object value, Duration timeout) {
        redisTemplate.opsForValue().set(key, value, timeout);
    }

    /**
     * 获取缓存值
     *
     * @param key 缓存键
     * @return 缓存值 (键不存在时返回 null)
     */
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 获取缓存集合
     *
     * @param key         缓存键
     * @param elementType 集合元素类型
     * @param <T>         集合元素类型
     * @return 缓存集合 (键不存在或缓存类型不匹配时返回 null)
     */
    public <T> List<T> getList(String key, Class<T> elementType) {
        Object cached = redisTemplate.opsForValue().get(key);
        if (!(cached instanceof List)) {
            return null;
        }

        List<?> cachedList = (List<?>) cached;
        List<T> result = new ArrayList<>(cachedList.size());
        for (Object item : cachedList) {
            if (!elementType.isInstance(item)) {
                return null;
            }
            result.add(elementType.cast(item));
        }

        return result;
    }

    /**
     * 删除缓存
     *
     * @param key 缓存键
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 删除缓存集合
     *
     * @param keys 缓存键集合
     */
    public void delete(Collection<String> keys) {
        redisTemplate.delete(keys);
    }

    /**
     * 设置字符串缓存
     *
     * @param key     缓存键
     * @param value   字符串值
     * @param timeout 过期时长 (不可为 null)
     */
    public void setString(String key, String value, Duration timeout) {
        stringRedisTemplate.opsForValue().set(key, value, timeout);
    }

    /**
     * 获取字符串缓存
     *
     * @param key 缓存键
     * @return 字符串值 (键不存在时返回 null)
     */
    public String getString(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 删除字符串缓存
     *
     * @param key 缓存键
     */
    public void deleteString(String key) {
        stringRedisTemplate.delete(key);
    }
}
