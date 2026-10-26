package cn.hoshiikunn.ezshop.result;

import lombok.Getter;

/**
 * 响应码
 * <p>
 * 通用响应码采用 HTTP 标准状态码(3位)；业务响应码由6位数字组成，
 * 第1位为错误类型，第2～3位为模块编码，第4～6位为业务编码。
 * </p>
 * <p>
 * 如：101001 (1 -> 错误类型，01 -> 认证模块，001 -> 具体响应码)
 * </p>
 *
 * <h3>错误类型 (第1位)</h3>
 * <ul>
 *     <li>1 - 客户端/业务错误(参数、认证、权限、业务校验等)</li>
 * </ul>
 *
 * <h3>模块编码 (第2~3位)</h3>
 * <ul>
 *     <li>1 01 - 认证模块 </li>
 * </ul>
 */
@Getter
public enum ResultCode {

    // ==================== HTTP 标准状态码 ====================
    SUCCESS(200, "成功"),
    BAD_REQUEST(400, "无效请求，请联系管理员"),
    UNAUTHORIZED(401, "登录信息已失效，请重新登录"),
    FORBIDDEN(403, "您没有权限"),
    NOT_FOUND(404, "请求地址不存在"),
    INTERNAL_SERVER_ERROR(500, "内部服务错误"),

    // ==================== 认证模块 - 1 01 xxx ====================
    USERNAME_OR_PASSWORD_ERROR(101001, "用户名或密码错误"),
    USER_NOT_FOUND(101002, "用户不存在"),

    ;

    // 响应码
    private final Integer code;
    // 响应信息
    private final String msg;

    ResultCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
