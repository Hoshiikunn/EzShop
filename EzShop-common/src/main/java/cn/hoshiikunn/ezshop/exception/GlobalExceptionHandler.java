package cn.hoshiikunn.ezshop.exception;

import cn.hoshiikunn.ezshop.result.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
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
}
