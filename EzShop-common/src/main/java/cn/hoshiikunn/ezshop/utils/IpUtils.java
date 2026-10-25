package cn.hoshiikunn.ezshop.utils;

import cn.hutool.core.util.StrUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * IP 工具类
 */
public class IpUtils {

    /**
     * 获取客户端 IP
     * <p>
     * 需由可信反向代理覆写请求头
     *
     * @param request Servlet 请求
     * @return IP 地址
     */
    public static String getClientIp(HttpServletRequest request) {
        // 先取 X-Real-IP
        String ip = request.getHeader("X-Real-IP");
        if (StrUtil.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            // X-Real-IP 没有则取 X-Forwarded-For
            String xff = request.getHeader("X-Forwarded-For");
            if (StrUtil.isNotBlank(xff)) {
                // 取链首(最初客户端地址)，链首可信的前提是代理覆写了XFF
                ip = xff.split(",")[0].trim();
            }
        }
        // X-Real-IP 和 X-Forwarded-For 都没有则 getRemoteAddr
        if (StrUtil.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        // 本机 IPv6 统一改成 IPv4 的 127.0.0.1
        if ("0:0:0:0:0:0:0:1".equals(ip)) {
            ip = "127.0.0.1";
        }

        return ip;
    }
}
