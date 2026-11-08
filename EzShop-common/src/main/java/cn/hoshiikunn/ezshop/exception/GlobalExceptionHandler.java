package cn.hoshiikunn.ezshop.exception;

import cn.hoshiikunn.ezshop.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static cn.hoshiikunn.ezshop.result.ResultCode.*;

/**
 * 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理业务异常
     *
     * @param bx 业务异常
     * @return {@code Result} 统一响应
     */
    @ExceptionHandler(BusinessException.class)
    public Result<?> handleBusinessException(BusinessException bx) {
        return new Result<>().error(bx.getCode(), bx.getMsg());
    }

    /**
     * 处理未授权异常
     *
     * @param ue 未授权异常
     * @return {@code Result} 统一响应
     */
    @ExceptionHandler(UnauthorizedException.class)
    public Result<?> handleUnauthorizedException(UnauthorizedException ue) {
        return new Result<>().error(FORBIDDEN);
    }

    /**
     * 处理数据库重复键异常
     *
     * @param dex 重复键异常
     * @return {@code Result} 统一响应
     */
    @ExceptionHandler(DuplicateKeyException.class)
    public Result<?> handleDuplicateKeyException(DuplicateKeyException dex) {
        return new Result<>().error(BAD_REQUEST.getCode(), "提交的信息与现有数据冲突，请刷新后重试");
    }

    /**
     * 处理未知异常 (兜底)
     *
     * @param e 异常
     * @return {@code Result} 统一响应
     */
    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception e) {
        log.error("全局异常：", e);
        return new Result<>().error(INTERNAL_SERVER_ERROR);
    }
}
