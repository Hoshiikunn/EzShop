package cn.hoshiikunn.ezshop.result;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

import static cn.hoshiikunn.ezshop.result.ResultCode.INTERNAL_SERVER_ERROR;
import static cn.hoshiikunn.ezshop.result.ResultCode.SUCCESS;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Schema(description = "统一响应")
public final class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "状态码: 200表示成功, 其他值表示失败")
    private final int code;

    @Schema(description = "消息内容")
    private final String msg;

    @Schema(description = "响应数据")
    private final T data;

    /**
     * 构建携带业务数据的成功响应
     *
     * @param data 业务数据
     * @param <T>  业务数据类型
     * @return 使用默认成功消息的响应
     */
    public static <T> Result<T> ok(T data) {
        return new Result<>(SUCCESS.getCode(), SUCCESS.getMsg(), data);
    }

    /**
     * 构建携带业务数据和自定义成功消息的响应
     *
     * @param data 业务数据，可为 {@code null}
     * @param msg  响应消息
     * @param <T>  业务数据类型
     * @return 使用自定义消息的成功响应
     */
    public static <T> Result<T> ok(T data, String msg) {
        return new Result<>(SUCCESS.getCode(), msg, data);
    }

    /**
     * 构建无业务数据的成功响应
     *
     * @return 使用默认成功消息的响应
     */
    public static Result<Void> ok() {
        return new Result<>(SUCCESS.getCode(), SUCCESS.getMsg(), null);
    }

    /**
     * 根据响应码枚举构建错误响应
     *
     * @param resultCode 错误响应码
     * @param <T>        业务数据类型，通常为 {@code Void}
     * @return 使用枚举错误码和错误消息的响应
     */
    public static <T> Result<T> error(IResultCode resultCode) {
        return new Result<>(resultCode.getCode(), resultCode.getMsg(), null);
    }

    /**
     * 根据响应码枚举和自定义消息构建错误响应
     *
     * @param resultCode 错误响应码
     * @param msg        覆盖枚举默认文案的响应消息
     * @param <T>        业务数据类型，通常为 {@code Void}
     * @return 使用枚举错误码和自定义消息的错误响应
     */
    public static <T> Result<T> error(IResultCode resultCode, String msg) {
        return new Result<>(resultCode.getCode(), msg, null);
    }

    /**
     * 根据错误码和消息直接构建错误响应
     *
     * @param code 错误码
     * @param msg  错误消息
     * @param <T>  业务数据类型，通常为 {@code Void}
     * @return 使用指定错误码和消息的错误响应
     */
    public static <T> Result<T> error(int code, String msg) {
        return new Result<>(code, msg, null);
    }

    /**
     * 构建无业务数据的失败响应
     *
     * @return 使用默认失败消息的响应
     */
    public static Result<Void> error() {
        return new Result<>(INTERNAL_SERVER_ERROR.getCode(), INTERNAL_SERVER_ERROR.getMsg(), null);
    }
}
