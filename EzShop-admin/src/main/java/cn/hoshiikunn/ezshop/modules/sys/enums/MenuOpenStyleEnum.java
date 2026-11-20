package cn.hoshiikunn.ezshop.modules.sys.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 菜单打开方式枚举类
 */
@Getter
@AllArgsConstructor
public enum MenuOpenStyleEnum {

    /**
     * 内部打开
     */
    INTERNAL(0),
    /**
     * 外部打开
     */
    EXTERNAL(1);

    private final Integer value;
}
