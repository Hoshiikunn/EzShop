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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
        // 根菜单校验，根节点允许目录(M)与菜单(C)，权限(F)不可挂根
        if (dto.getPid() == 0L) {
            if (PERMISSION.getValue().equals(dto.getMenuType())) {
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

            // 父子类型约束，目录下挂菜单、菜单下挂权限、权限不可挂载子节点
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMenu(SysMenuDTO dto) {
        // 菜单存在性
        SysMenuEntity oldEntity = this.getById(dto.getId());
        if (oldEntity == null) {
            throw new BusinessException(MENU_NOT_FOUND, "被修改的菜单不存在");
        }

        // 菜单类型不可变更
        if (!oldEntity.getMenuType().equals(dto.getMenuType())) {
            throw new BusinessException(MENU_TYPE_NOT_MODIFIABLE);
        }

        // 更换父菜单，根节点允许目录(M)与菜单(C)，权限(F)不可挂根
        SysMenuEntity newParent = null;
        if (dto.getPid() == 0L) {
            if (PERMISSION.getValue().equals(dto.getMenuType())) {
                throw new BusinessException(MENU_TYPE_MISMATCH);
            }
        } else if (!oldEntity.getPid().equals(dto.getPid())) {
            newParent = this.getById(dto.getPid());
            if (newParent == null) {
                throw new BusinessException(MENU_NOT_FOUND, "父菜单不存在");
            }
            validateParentChildRelation(newParent.getMenuType(), dto.getMenuType());
        }

        // 类型字段约束 + 修改场景校验
        MenuValidator validator = validatorRegistry.getValidator(dto.getMenuType());
        validator.validateFields(dto);
        validator.validateOnUpdate(dto, oldEntity, newParent);

        // 防环，新父不能是自身或自身后代
        if (newParent != null && checkParentCycle(dto.getId(), dto.getPid())) {
            throw new BusinessException(MENU_PARENT_CYCLE);
        }

        // 同级名称唯一
        checkSameLevelMenuName(dto);

        // 写库
        SysMenuEntity updateEntity = BeanUtil.toBean(dto, SysMenuEntity.class);
        this.updateById(updateEntity);
    }

    /**
     * 防环，沿新父的祖先链上溯，命中自身即成环
     */
    private boolean checkParentCycle(Long menuId, Long newPid) {
        Long currentId = newPid;
        Set<Long> visited = new HashSet<>();
        while (currentId != null && currentId != 0L && visited.add(currentId)) {
            if (menuId.equals(currentId)) {
                return true;
            }
            SysMenuEntity ancestor = this.getById(currentId);
            currentId = ancestor == null ? null : ancestor.getPid();
        }
        return false;
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
        // 菜单下只能挂权限
        if (MENU.getValue().equals(parentType) && !PERMISSION.getValue().equals(childType)) {
            throw new BusinessException(MENU_TYPE_MISMATCH);
        }
        // 权限不可挂载子节点
        if (PERMISSION.getValue().equals(parentType)) {
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
                // 修改场景
                .ne(dto.getId() != null, SysMenuEntity::getId, dto.getId())
                .count();
        if (nameCount > 0) {
            throw new BusinessException(MENU_NAME_EXISTS);
        }
    }
}
