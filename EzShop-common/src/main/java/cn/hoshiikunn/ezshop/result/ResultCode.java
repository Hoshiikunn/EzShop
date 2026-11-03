package cn.hoshiikunn.ezshop.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 通用响应码
 * <p>
 * 只保留跨服务协议级状态码，两个服务的客户端按同一语义解析；
 * 业务响应码由各服务的枚举实现 {@link IResultCode} 定义
 */
@Getter
@AllArgsConstructor
public enum ResultCode implements IResultCode {

    // ==================== HTTP 标准状态码 ====================
    SUCCESS(200, "成功"),
    BAD_REQUEST(400, "无效请求，请联系管理员"),
    UNAUTHORIZED(401, "登录信息已失效，请重新登录"),
    FORBIDDEN(403, "您没有权限"),
    NOT_FOUND(404, "请求地址不存在"),
    INTERNAL_SERVER_ERROR(500, "内部服务错误"),

    ;

    // 响应码
    private final Integer code;
    // 消息内容
    private final String msg;
}
