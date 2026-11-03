package cn.hoshiikunn.ezshop.modules.sys.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 系统用户状态枚举类
 */
@Getter
@AllArgsConstructor
public enum SysUserStatusEnum {

    /**
     * 启用
     */
    ENABLE(1),
    /**
     * 禁用
     */
    DISABLED(0);

    private final Integer value;
}
