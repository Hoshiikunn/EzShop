package cn.hoshiikunn.ezshop.modules.sys.validator;

import cn.hoshiikunn.ezshop.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.hoshiikunn.ezshop.common.result.AdminResultCode.MENU_TYPE_MISMATCH;

/**
 * 菜单校验器注册器
 */
@Component
@RequiredArgsConstructor
public class MenuValidatorRegistry {

    private final List<MenuValidator> validators;

    private Map<String, MenuValidator> validatorMap;

    @PostConstruct
    public void init() {
        validatorMap = new HashMap<>();
        for (MenuValidator validator : validators) {
            validatorMap.put(validator.getMenuType(), validator);
        }
    }

    /**
     * 根据菜单类型获取校验器
     *
     * @param menuType 菜单类型
     * @return 对应的校验器
     */
    public MenuValidator getValidator(String menuType) {
        MenuValidator validator = validatorMap.get(menuType);
        if (validator == null) {
            throw new BusinessException(MENU_TYPE_MISMATCH);
        }
        return validator;
    }
}
