package cn.hoshiikunn.ezshop.modules.sys.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 操作状态枚举类
 */
@Getter
@AllArgsConstructor
public enum OperStatusEnum {

    /**
     * 失败
     */
    FAIL(0),
    /**
     * 成功
     */
    SUCCESS(1);

    private final Integer value;
}
