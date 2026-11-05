package cn.hoshiikunn.ezshop.modules.sys.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 删除标记枚举类
 */
@Getter
@AllArgsConstructor
public enum DeleteFlagEnum {
    /**
     * 未删除
     */
    NOT_DELETED(0),
    /**
     * 已删除
     */
    DELETED(1);

    private final Integer value;
}
