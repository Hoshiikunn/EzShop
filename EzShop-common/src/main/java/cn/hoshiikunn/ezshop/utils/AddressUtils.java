package cn.hoshiikunn.ezshop.utils;

import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.StrUtil;
import org.lionsoul.ip2region.xdb.Searcher;
import org.lionsoul.ip2region.xdb.Version;

import java.io.InputStream;

/**
 * IP 归属地工具类
 * <p>
 * 基于 ip2region 离线数据库解析归属地，查询失败一律返回 未知
 */
public final class AddressUtils {

    /**
     * ip2region v4 数据文件类路径
     */
    private static final String XDB_RESOURCE = "/ip2region/ip2region_v4.xdb";
    /**
     * 内网 IP 归属地文案
     */
    private static final String INNER_IP = "内网IP";
    /**
     * 解析失败兜底文案
     */
    private static final String UNKNOWN = "未知";
    /**
     * 归属地最大长度
     * <p>
     * 对应 sys_login_log.login_location 列宽
     */
    private static final int MAX_LOCATION_LENGTH = 100;

    /**
     * v4 数据库查询器
     * <p>
     * 延迟加载且非线程安全，仅允许在 {@code synchronized} 方法内访问
     */
    private static Searcher searcher;

    private AddressUtils() {
    }

    /**
     * 解析 IP 归属地。查询失败一律返回 未知
     * <p>
     * 内网 IP 直接返回 内网IP，IPv6、解析失败或数据文件缺失时一律返回 未知
     *
     * @param ip 字面量 IP 地址 (仅支持 IPv4)
     * @return 归属地描述，如 中国 广东省 深圳市 电信
     */
    public static synchronized String getRealAddress(String ip) {
        // 空值与 IPv6 暂不解析
        if (StrUtil.isBlank(ip) || StrUtil.contains(ip, ':')) {
            return UNKNOWN;
        }
        // 内网 IP 无需查询
        if (NetUtil.isInnerIP(ip)) {
            return INNER_IP;
        }
        try {
            Searcher s = getSearcher();
            // 数据文件缺失或加载失败时降级
            if (s == null) {
                return UNKNOWN;
            }
            // 查询并格式化归属地
            return formatRegion(s.search(ip));
        } catch (Exception e) {
            return UNKNOWN;
        }
    }

    /**
     * 获取 v4 数据库查询器
     * <p>
     * 首次调用时从类路径加载 xdb 数据文件整库缓存进内存，加载成功后全局复用
     *
     * @return 查询器 <p>
     * 数据文件缺失或加载失败时返回 {@code null}
     */
    private static Searcher getSearcher() {
        // 已初始化时直接复用
        if (searcher != null) {
            return searcher;
        }
        try (InputStream in = AddressUtils.class.getResourceAsStream(XDB_RESOURCE)) {
            // 数据文件缺失时降级
            if (in == null) {
                return null;
            }
            // 整库加载进内存，单次查询约 10 微秒
            searcher = Searcher.newWithBuffer(Version.IPv4,
                    Searcher.loadContentFromInputStream(in));
        } catch (Exception e) {
            // 加载失败降级
            return null;
        }
        return searcher;
    }

    /**
     * 格式化归属地描述
     * <p>
     * ip2region v3 返回格式为 国家|省份|城市|运营商|ISO代码，如 中国|广东省|深圳市|电信|CN
     *
     * @param region xdb 原始返回值
     * @return 归属地描述，如 中国 广东省 深圳市 电信 <p>
     * 空值或全部分段无效时返回 未知，超长时按 login_location 列宽截断
     */
    private static String formatRegion(String region) {
        // 空值兜底
        if (StrUtil.isBlank(region)) {
            return UNKNOWN;
        }
        // 按竖线拆分五段
        String[] parts = region.split("\\|");
        StringBuilder sb = new StringBuilder();
        // 丢弃末尾 ISO 代码，只拼接国家/省份/城市/运营商
        for (int i = 0; i < parts.length && i < 4; i++) {
            // 跳过空白段与无效占位段 (0)
            if (StrUtil.isNotBlank(parts[i]) && !"0".equals(parts[i])) {
                // 段间以空格分隔
                if (sb.length() > 0) {
                    sb.append(' ');
                }
                sb.append(parts[i]);
            }
        }
        // 拼接结果为空时兜底，超长时按列宽截断
        return sb.length() == 0 ? UNKNOWN
                : StrUtil.subPre(sb.toString(), MAX_LOCATION_LENGTH);
    }
}
