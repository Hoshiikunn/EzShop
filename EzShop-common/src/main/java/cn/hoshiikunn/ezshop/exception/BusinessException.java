package cn.hoshiikunn.ezshop.exception;

import cn.hoshiikunn.ezshop.result.ResultCode;
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
    public BusinessException(ResultCode resultCode) {
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
    public BusinessException(ResultCode resultCode, Throwable e) {
        super(resultCode.getMsg(), e);
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }
}
