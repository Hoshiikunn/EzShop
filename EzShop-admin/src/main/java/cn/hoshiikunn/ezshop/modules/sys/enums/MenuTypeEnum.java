package cn.hoshiikunn.ezshop.modules.sys.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 菜单类型枚举类
 */
@Getter
@AllArgsConstructor
public enum MenuTypeEnum {

    /**
     * 目录
     */
    DIRECTORY("M"),

    /**
     * 菜单
     */
    MENU("C"),

    /**
     * 按钮
     */
    BUTTON("F");

    private final String value;
}
