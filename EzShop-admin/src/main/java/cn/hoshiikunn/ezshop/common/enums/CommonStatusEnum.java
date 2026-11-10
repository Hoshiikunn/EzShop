package cn.hoshiikunn.ezshop.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 通用状态枚举类
 */
@Getter
@AllArgsConstructor
public enum CommonStatusEnum {

    /**
     * 启用
     */
    ENABLE(1),
    /**
     * 禁用
     */
    DISABLE(0);

    private final Integer value;
}
