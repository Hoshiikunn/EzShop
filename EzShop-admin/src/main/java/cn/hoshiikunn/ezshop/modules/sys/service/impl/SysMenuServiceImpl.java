package cn.hoshiikunn.ezshop.modules.sys.service.impl;

import cn.hoshiikunn.ezshop.base.BaseServiceImpl;
import cn.hoshiikunn.ezshop.exception.BusinessException;
import cn.hoshiikunn.ezshop.modules.sys.dao.SysMenuDao;
import cn.hoshiikunn.ezshop.modules.sys.dto.SysMenuDTO;
import cn.hoshiikunn.ezshop.modules.sys.entity.SysMenuEntity;
import cn.hoshiikunn.ezshop.modules.sys.service.SysMenuService;
import cn.hoshiikunn.ezshop.modules.sys.validator.MenuValidator;
import cn.hoshiikunn.ezshop.modules.sys.validator.MenuValidatorRegistry;
import cn.hoshiikunn.ezshop.modules.sys.vo.SysMenuInfoVO;
import cn.hoshiikunn.ezshop.modules.sys.vo.SysMenuListVO;
import cn.hoshiikunn.ezshop.modules.sys.vo.SysMenuTreeVO;
import cn.hoshiikunn.ezshop.utils.TreeUtils;
import cn.hutool.core.bean.BeanUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static cn.hoshiikunn.ezshop.common.result.AdminResultCode.*;
import static cn.hoshiikunn.ezshop.modules.sys.enums.MenuTypeEnum.*;

/**
 * 菜单服务实现类
 */
@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl extends BaseServiceImpl<SysMenuDao, SysMenuEntity> implements SysMenuService {

    private final MenuValidatorRegistry validatorRegistry;

    @Override
    public List<SysMenuTreeVO> getMenuTree() {
        // 查询全量菜单
        List<SysMenuEntity> menus = this.lambdaQuery()
                .orderByAsc(SysMenuEntity::getSort)
                .orderByAsc(SysMenuEntity::getId)
                .list();

        // 实体转换 VO
        List<SysMenuTreeVO> nodes = menus.stream()
                .map(menu -> BeanUtil.toBean(menu, SysMenuTreeVO.class))
                .collect(Collectors.toList());

        // 组装全量菜单树
        return TreeUtils.build(
                nodes,
                SysMenuTreeVO::getId,
                SysMenuTreeVO::getPid,
                (parent, child) -> parent.getChildren().add(child),
                pid -> pid == null || pid == 0L,
                Comparator.comparing(SysMenuTreeVO::getSort)
                        .thenComparing(SysMenuTreeVO::getId)
        );
    }

    @Override
    public SysMenuInfoVO getMenuInfo(Long menuId) {
        SysMenuEntity menuEntity = this.getById(menuId);
        if (menuEntity == null) {
            throw new BusinessException(MENU_NOT_FOUND);
        }

        return BeanUtil.toBean(menuEntity, SysMenuInfoVO.class);
    }

    @Override
    public List<SysMenuListVO> getMenuList() {
        // 查询全量菜单
        List<SysMenuEntity> menus = this.lambdaQuery()
                .orderByAsc(SysMenuEntity::getSort)
                .orderByAsc(SysMenuEntity::getId)
                .list();

        // 实体转换 VO
        return menus.stream()
                .map(SysMenuListVO::of)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addMenu(SysMenuDTO dto) {
        // 根菜单校验，根节点允许目录(M)与菜单(C)，按钮(F)不可挂根
        if (dto.getPid() == 0L) {
            if (BUTTON.getValue().equals(dto.getMenuType())) {
                throw new BusinessException(MENU_TYPE_MISMATCH);
            }
            // 字段约束校验
            validatorRegistry.getValidator(dto.getMenuType()).validateFields(dto);
        } else {
            // 子菜单，父菜单必须存在
            SysMenuEntity parent = this.getById(dto.getPid());
            if (parent == null) {
                throw new BusinessException(MENU_NOT_FOUND);
            }

            // 父子类型约束，目录下挂菜单、菜单下挂按钮、按钮不可挂载子节点
            validateParentChildRelation(parent.getMenuType(), dto.getMenuType());

            // 字段约束校验 + 新增场景校验
            MenuValidator validator = validatorRegistry.getValidator(dto.getMenuType());
            validator.validateFields(dto);
            validator.validateOnCreate(dto, parent);
        }

        // 同级名称唯一
        checkSameLevelMenuName(dto);

        // 填充字段并写库
        SysMenuEntity entity = BeanUtil.toBean(dto, SysMenuEntity.class);
        entity.setId(null);
        this.save(entity);
    }

    /**
     * 校验父子类型约束
     *
     * @param parentType 父菜单类型
     * @param childType  子菜单类型
     */
    private void validateParentChildRelation(String parentType, String childType) {
        // 目录下只能挂菜单
        if (DIRECTORY.getValue().equals(parentType) && !MENU.getValue().equals(childType)) {
            throw new BusinessException(MENU_TYPE_MISMATCH);
        }
        // 菜单下只能挂按钮
        if (MENU.getValue().equals(parentType) && !BUTTON.getValue().equals(childType)) {
            throw new BusinessException(MENU_TYPE_MISMATCH);
        }
        // 按钮不可挂载子节点
        if (BUTTON.getValue().equals(parentType)) {
            throw new BusinessException(MENU_TYPE_MISMATCH);
        }
    }

    /**
     * 检查同级菜单名称是否重复
     *
     * @param dto 菜单参数
     */
    private void checkSameLevelMenuName(SysMenuDTO dto) {
        long nameCount = this.lambdaQuery()
                .eq(SysMenuEntity::getPid, dto.getPid())
                .eq(SysMenuEntity::getMenuName, dto.getMenuName())
                .count();
        if (nameCount > 0) {
            throw new BusinessException(MENU_NAME_EXISTS);
        }
    }
}
