package cn.hoshiikunn.ezshop.modules.sys.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 是否超级管理员 枚举类
 */
@Getter
@AllArgsConstructor
public enum IsSuperAdminEnum {
    /**
     * 是超级管理员
     */
    IS_SUPER_ADMIN(1),
    /**
     * 不是超级管理员
     */
    NOT_SUPER_ADMIN(0);

    private final Integer value;
}
