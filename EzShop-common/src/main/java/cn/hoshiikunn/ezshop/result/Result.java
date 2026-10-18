package cn.hoshiikunn.ezshop.result;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

import static cn.hoshiikunn.ezshop.result.ResultCode.INTERNAL_SERVER_ERROR;
import static cn.hoshiikunn.ezshop.result.ResultCode.SUCCESS;

@Data
@Schema(description = "统一响应")
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 状态码: 200表示成功, 其他值表示失败
     */
    @Schema(description = "状态码: 200表示成功, 其他值表示失败")
    private int code;

    /**
     * 消息内容
     */
    @Schema(description = "消息内容")
    private String msg;

    /**
     * 响应数据
     */
    @Schema(description = "响应数据")
    private T data;

    /**
     * 无数据的成功
     *
     * @return 封装后的的统一结果 - {@code Result}
     */
    public Result<T> ok() {
        this.code = SUCCESS.getCode();
        this.msg = SUCCESS.getMsg();
        return this;
    }

    /**
     * 有数据的成功
     *
     * @param data 响应数据
     * @return 封装后的的统一结果 - {@code Result}
     */
    public Result<T> ok(T data) {
        this.code = SUCCESS.getCode();
        this.msg = SUCCESS.getMsg();
        this.setData(data);
        return this;
    }

    /**
     * 指定消息内容的成功
     *
     * @param msg 消息内容
     * @return 封装后的的统一结果 - {@code Result}
     */
    public Result<T> ok(String msg) {
        this.code = SUCCESS.getCode();
        this.msg = msg;
        return this;
    }

    /**
     * 指定消息内容和数据的成功
     *
     * @param data 响应数据
     * @param msg  消息内容
     * @return 封装后的的统一结果 - {@code Result}
     */
    public Result<T> ok(T data, String msg) {
        this.code = SUCCESS.getCode();
        this.msg = msg;
        this.setData(data);
        return this;
    }

    /**
     * 默认失败
     *
     * @return 500响应码及对应的响应信息的统一结果 - {@code Result}
     */
    public Result<T> error() {
        this.code = INTERNAL_SERVER_ERROR.getCode();
        this.msg = INTERNAL_SERVER_ERROR.getMsg();
        return this;
    }

    /**
     * 指定响应码枚举的失败
     *
     * @param resultCode 响应码枚举
     * @return 封装后的的统一结果 - {@code Result}
     */
    public Result<T> error(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
        return this;
    }
}
