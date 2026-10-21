package cn.hoshiikunn.ezshop.utils;

import cn.hoshiikunn.ezshop.exception.BusinessException;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;


import java.util.Set;

import static cn.hoshiikunn.ezshop.result.ResultCode.BAD_REQUEST;

/**
 * 校验工具类
 * <p>
 * 手动触发 Jakarta Bean Validation 校验，支持 DTO 分组校验，校验失败抛出业务异常
 */
public final class ValidatorUtils {

    private ValidatorUtils() {
    }

    // 校验器实例
    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * 校验实体参数，校验失败抛出业务异常
     *
     * @param object 校验实体
     * @param groups 校验分组，不传时校验 Default 分组
     */
    public static void validateEntity(Object object, Class<?>... groups) {
        // 执行校验
        Set<ConstraintViolation<Object>> violations = validator.validate(object, groups);
        // 取出第一个校验失败的提示信息
        if (!violations.isEmpty()) {
            ConstraintViolation<Object> constraint = violations.iterator().next();
            throw new BusinessException(BAD_REQUEST.getCode(), constraint.getMessage());
        }
    }

}
