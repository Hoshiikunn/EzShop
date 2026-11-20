package cn.hoshiikunn.ezshop.exception;

import cn.hoshiikunn.ezshop.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Comparator;

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
    public Result<Void> handleBusinessException(BusinessException bx) {
        return Result.error(bx.getCode(), bx.getMsg());
    }

    /**
     * 处理未授权异常
     *
     * @param ue 未授权异常
     * @return {@code Result} 统一响应
     */
    @ExceptionHandler(UnauthorizedException.class)
    public Result<Void> handleUnauthorizedException(UnauthorizedException ue) {
        return Result.error(FORBIDDEN);
    }

    /**
     * 处理参数绑定校验异常
     *
     * @param e 参数绑定校验异常
     * @return {@code Result} 统一响应
     */
    @ExceptionHandler(BindException.class)
    public Result<Void> handleBindException(BindException e) {
        return Result.error(BAD_REQUEST.getCode(), resolveFirstMessage(e));
    }

    /**
     * 获取排序后的第一条校验错误提示
     * <p>
     * Bean Validation 的违规项集合不保证顺序，这里显式排序以保证提示稳定
     *
     * @param e 参数绑定校验异常
     * @return 第一条错误提示
     */
    private String resolveFirstMessage(BindException e) {
        return e.getFieldErrors().stream()
                .min(Comparator.comparing(FieldError::getField)
                        .thenComparing(this::resolveErrorMessage))
                .map(this::resolveErrorMessage)
                .orElseGet(() -> e.getAllErrors().stream()
                        .min(Comparator.comparing(ObjectError::getObjectName)
                                .thenComparing(this::resolveErrorMessage))
                        .map(this::resolveErrorMessage)
                        .orElse(BAD_REQUEST.getMsg()));
    }

    /**
     * 解析错误提示
     * <p>
     * Bean Validation 未显式声明消息时，DefaultMessage 可能为 null
     *
     * @param error 校验错误
     * @return 错误提示
     */
    private String resolveErrorMessage(ObjectError error) {
        return error.getDefaultMessage() == null ? BAD_REQUEST.getMsg() : error.getDefaultMessage();
    }

    /**
     * 处理数据库重复键异常
     *
     * @param dex 重复键异常
     * @return {@code Result} 统一响应
     */
    @ExceptionHandler(DuplicateKeyException.class)
    public Result<Void> handleDuplicateKeyException(DuplicateKeyException dex) {
        return Result.error(BAD_REQUEST.getCode(), "提交的信息与现有数据冲突，请刷新后重试");
    }

    /**
     * 处理方法参数类型不匹配异常
     *
     * @param matme 方法参数类型不匹配异常
     * @return 统一响应
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Result<Void> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException matme) {
        return Result.error(BAD_REQUEST);
    }

    /**
     * 处理未知异常 (兜底)
     *
     * @param e 异常
     * @return {@code Result} 统一响应
     */
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) {
        log.error("全局异常：", e);
        return Result.error(INTERNAL_SERVER_ERROR);
    }
}
