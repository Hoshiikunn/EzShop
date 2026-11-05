package cn.hoshiikunn.ezshop.common.result;

import cn.hoshiikunn.ezshop.result.IResultCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 后台管理业务响应码
 *
 * <h5>模块号段登记</h5>
 * <ul>
 *     <li>101 - 认证模块 </li>
 *     <li>102 - 用户模块 </li>
 * </ul>
 */
@Getter
@AllArgsConstructor
public enum AdminResultCode implements IResultCode {

    // ==================== 认证模块 - 101 ====================
    USERNAME_OR_PASSWORD_ERROR(101001, "用户名或密码错误"),

    // ==================== 用户模块 - 102 ====================
    USER_NOT_FOUND(102001, "用户不存在"),
    USER_USERNAME_EXISTS(102002, "用户名已存在"),
    USER_MOBILE_EXISTS(102003, "该手机号已被使用"),
    USER_EMAIL_EXISTS(102004, "该邮箱已被使用"),
    USER_SUPER_ADMIN_DELETE_FORBIDDEN(102005, "禁止删除超级管理员"),
    USER_SELF_DELETE_FORBIDDEN(102006, "不能删除当前登录账号"),

    ;

    // 响应码
    private final Integer code;
    // 消息内容
    private final String msg;
}
