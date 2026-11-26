package cn.hoshiikunn.ezshop.common.annotation;

import java.lang.annotation.*;

/**
 * 操作日志注解类
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperLog {

    /**
     * 所属模块
     * <p>
     * 如 菜单管理
     */
    String module();

    /**
     * 操作内容
     * <p>
     * 如 添加菜单
     */
    String operation();

    /**
     * 排除的敏感参数字段名
     */
    String[] excludeParamNames() default {};
}
