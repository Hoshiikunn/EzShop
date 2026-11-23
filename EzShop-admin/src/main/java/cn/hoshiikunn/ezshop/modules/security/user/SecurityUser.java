package cn.hoshiikunn.ezshop.modules.security.user;

import cn.hoshiikunn.ezshop.exception.BusinessException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import static cn.hoshiikunn.ezshop.result.ResultCode.UNAUTHORIZED;

/**
 * 当前登录用户工具
 */
public class SecurityUser {

    private SecurityUser() {
    }

    /**
     * 获取当前 Subject
     * <p>
     * 非 Web 线程 (定时任务/MQ/启动期) 或 Shiro 上下文未绑定时返回 null
     *
     * @return 当前 Subject；不可用时返回 null
     */
    public static Subject getSubject() {
        try {
            return SecurityUtils.getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取当前登录用户 ID
     * <p>
     * 未认证 (principal 为空) 或身份类型不符时返回 null，调用方需自行判空
     *
     * @return 当前 userId；无登录上下文时返回 null
     */
    public static Long getUserId() {
        Subject subject = getSubject();
        if (subject == null) {
            return null;
        }

        Object principal = subject.getPrincipal();
        return principal instanceof Long ? (Long) principal : null;
    }

    /**
     * 获取当前登录用户 ID
     * <p>
     * 要求当前线程必须存在登录上下文
     *
     * @return 当前 userId
     * @throws BusinessException 登录上下文缺失或身份类型不符时抛出
     */
    public static Long requireUserId() {
        Long userId = getUserId();
        if (userId == null) {
            throw new BusinessException(UNAUTHORIZED);
        }

        return userId;
    }
}
