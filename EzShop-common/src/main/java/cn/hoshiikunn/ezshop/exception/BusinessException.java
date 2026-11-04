package cn.hoshiikunn.ezshop.exception;

import cn.hoshiikunn.ezshop.result.IResultCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 业务异常类
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    // 响应码
    private int code;
    // 消息内容
    private String msg;

    /**
     * 指定响应码枚举构造业务异常
     *
     * @param resultCode 响应码枚举
     */
    public BusinessException(IResultCode resultCode) {
        super(resultCode.getMsg());
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }

    /**
     * 指定响应码枚举和原始异常构造业务异常
     *
     * @param resultCode 响应码枚举
     * @param e          原始异常
     */
    public BusinessException(IResultCode resultCode, Throwable e) {
        super(resultCode.getMsg(), e);
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }

    /**
     * 指定响应码枚举和自定义消息构造业务异常
     * <p>
     * 响应码取自枚举，消息以自定义内容覆盖枚举默认文案
     *
     * @param resultCode 响应码枚举 (提供 code)
     * @param msg        自定义消息内容 (覆盖枚举默认文案)
     */
    public BusinessException(IResultCode resultCode, String msg) {
        super(msg);
        this.code = resultCode.getCode();
        this.msg = msg;
    }

    /**
     * 指定响应码和消息内容构造业务异常
     *
     * @param code 响应码
     * @param msg  消息内容
     */
    public BusinessException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
}
