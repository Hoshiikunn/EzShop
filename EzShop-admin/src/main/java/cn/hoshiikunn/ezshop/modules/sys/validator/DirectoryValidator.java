package cn.hoshiikunn.ezshop.modules.sys.validator;

import cn.hoshiikunn.ezshop.exception.BusinessException;
import cn.hoshiikunn.ezshop.modules.sys.dto.SysMenuDTO;
import cn.hoshiikunn.ezshop.modules.sys.entity.SysMenuEntity;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Component;

import static cn.hoshiikunn.ezshop.common.result.AdminResultCode.*;
import static cn.hoshiikunn.ezshop.modules.sys.enums.MenuTypeEnum.DIRECTORY;

/**
 * 目录类型校验器
 */
@Component
public class DirectoryValidator implements MenuValidator {

    @Override
    public String getMenuType() {
        return DIRECTORY.getValue();
    }

    @Override
    public void validateFields(SysMenuDTO dto) {
        // 不可设置权限标识
        if (StrUtil.isNotBlank(dto.getPerms())) {
            throw new BusinessException(MENU_PERMS_NOT_ALLOWED, "目录不可设置权限标识");
        }
        // 可见性为必填项
        if (dto.getVisible() == null) {
            throw new BusinessException(MENU_VISIBLE_REQUIRED, "目录可见性为必填项");
        }
        // 目录不可设置打开方式
        if (dto.getOpenStyle() != null) {
            throw new BusinessException(MENU_OPEN_STYLE_NOT_ALLOWED, "目录不可设置打开方式");
        }
    }

    @Override
    public void validateOnUpdate(SysMenuDTO dto, SysMenuEntity oldEntity, SysMenuEntity newParent) {
        // 目录必须挂根，不可修改父菜单
        if (dto.getPid() != 0L) {
            throw new BusinessException(MENU_TYPE_MISMATCH);
        }
    }
}
