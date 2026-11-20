package cn.hoshiikunn.ezshop.modules.sys.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 菜单可见性枚举类
 */
@Getter
@AllArgsConstructor
public enum MenuVisibleEnum {

    /**
     * 隐藏
     */
    HIDDEN(0),
    /**
     * 显示
     */
    VISIBLE(1);

    private final Integer value;
}
