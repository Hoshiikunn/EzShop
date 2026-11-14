package cn.hoshiikunn.ezshop.modules.sys.service.impl;

import cn.hoshiikunn.ezshop.base.BaseServiceImpl;
import cn.hoshiikunn.ezshop.exception.BusinessException;
import cn.hoshiikunn.ezshop.modules.sys.dao.SysRoleDao;
import cn.hoshiikunn.ezshop.modules.sys.dto.SysRoleDTO;
import cn.hoshiikunn.ezshop.modules.sys.dto.SysRolePageDTO;
import cn.hoshiikunn.ezshop.modules.sys.dto.SysRoleStatusDTO;
import cn.hoshiikunn.ezshop.modules.sys.entity.SysMenuEntity;
import cn.hoshiikunn.ezshop.modules.sys.entity.SysRoleEntity;
import cn.hoshiikunn.ezshop.modules.sys.service.SysRoleMenuService;
import cn.hoshiikunn.ezshop.modules.sys.service.SysRoleService;
import cn.hoshiikunn.ezshop.modules.sys.service.SysUserRoleService;
import cn.hoshiikunn.ezshop.modules.sys.vo.SysMenuBriefVO;
import cn.hoshiikunn.ezshop.modules.sys.vo.SysRoleInfoVO;
import cn.hoshiikunn.ezshop.modules.sys.vo.SysRoleListVO;
import cn.hoshiikunn.ezshop.modules.sys.vo.SysRolePageVO;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static cn.hoshiikunn.ezshop.common.enums.CommonStatusEnum.ENABLE;
import static cn.hoshiikunn.ezshop.common.result.AdminResultCode.*;

/**
 * 角色服务实现类
 */
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl extends BaseServiceImpl<SysRoleDao, SysRoleEntity> implements SysRoleService {

    private final SysRoleMenuService roleMenuService;
    private final SysUserRoleService userRoleService;

    @Override
    public Page<SysRolePageVO> pageRole(SysRolePageDTO dto) {

        // 构建分页实体
        Page<SysRoleEntity> page = new Page<>(dto.getCurrent(), dto.getSize());

        // 组装查询条件
        LambdaQueryWrapper<SysRoleEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(dto.getRoleName()), SysRoleEntity::getRoleName, dto.getRoleName())
                .like(StrUtil.isNotBlank(dto.getRoleKey()), SysRoleEntity::getRoleKey, dto.getRoleKey())
                .eq(dto.getStatus() != null, SysRoleEntity::getStatus, dto.getStatus())
                .ge(dto.getCreateTimeBegin() != null, SysRoleEntity::getCreateTime, dto.getCreateTimeBegin())
                .le(dto.getCreateTimeEnd() != null, SysRoleEntity::getCreateTime, dto.getCreateTimeEnd())
                .orderByAsc(SysRoleEntity::getSort)
                .orderByDesc(SysRoleEntity::getCreateTime)
                .orderByDesc(SysRoleEntity::getId);

        // 分页查询并转换 VO
        Page<SysRoleEntity> result = this.page(page, wrapper);
        return (Page<SysRolePageVO>) result.convert(SysRolePageVO::of);
    }

    @Override
    public SysRoleInfoVO getRoleInfo(Long id) {
        SysRoleEntity roleEntity = this.getById(id);
        if (roleEntity == null) {
            throw new BusinessException(ROLE_NOT_FOUND);
        }

        // 实体转换VO后 获取菜单列表并填充
        SysRoleInfoVO info = BeanUtil.toBean(roleEntity, SysRoleInfoVO.class);
        List<SysMenuEntity> menus = roleMenuService.getMenusByRoleId(id);
        info.setMenus(menus.stream().map(SysMenuBriefVO::of).collect(Collectors.toList()));

        return info;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addRole(SysRoleDTO dto) {
        // 检查权限字符唯一性
        LambdaQueryWrapper<SysRoleEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRoleEntity::getRoleKey, dto.getRoleKey());
        if (this.count(wrapper) > 0) {
            throw new BusinessException(ROLE_KEY_EXISTS);
        }

        // 补充字段并写库
        SysRoleEntity addEntity = BeanUtil.copyProperties(dto, SysRoleEntity.class);
        addEntity.setId(null);
        addEntity.setStatus(ENABLE.getValue());
        try {
            this.save(addEntity);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(ROLE_KEY_EXISTS);
        }

        // 保存角色菜单关系
        roleMenuService.saveOrUpdate(addEntity.getId(), dto.getMenuIds());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRole(SysRoleDTO dto) {
        // 检查角色存在性
        SysRoleEntity roleEntity = this.getById(dto.getId());
        if (roleEntity == null) {
            throw new BusinessException(ROLE_NOT_FOUND, "被修改的角色不存在");
        }

        // 权限字符不可修改
        if (dto.getRoleKey() != null) {
            throw new BusinessException(ROLE_KEY_NOT_MODIFIABLE);
        }

        // 更新角色基础信息
        LambdaUpdateWrapper<SysRoleEntity> updateWrapper = new LambdaUpdateWrapper<SysRoleEntity>()
                .eq(SysRoleEntity::getId, roleEntity.getId())
                .set(SysRoleEntity::getRoleName, dto.getRoleName())
                .set(SysRoleEntity::getSort, dto.getSort())
                .set(SysRoleEntity::getRemark, StrUtil.blankToDefault(dto.getRemark(), null));

        boolean updated = this.update(new SysRoleEntity(), updateWrapper);
        if (!updated) {
            throw new BusinessException(ROLE_NOT_FOUND, "被修改的角色不存在");
        }

        // 全量替换角色菜单绑定
        roleMenuService.saveOrUpdate(roleEntity.getId(), dto.getMenuIds());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRole(Long id, boolean force) {
        // 检查角色存在性
        SysRoleEntity roleEntity = this.getById(id);
        if (roleEntity == null) {
            throw new BusinessException(ROLE_NOT_FOUND, "被删除的角色不存在");
        }

        // 查询仍绑定该角色的用户数量
        long userCount = userRoleService.countByRoleId(id);
        // 有用户绑定且未强制删除时，拒绝删除
        if (userCount > 0 && !force) {
            throw new BusinessException(
                    ROLE_HAS_USERS,
                    "角色已绑定 " + userCount + " 个用户，请确认后强制删除"
            );
        }

        // 如果强制删除，先解除用户角色绑定
        if (userCount > 0) {
            userRoleService.deleteByRoleId(id);
        }
        // 清理角色菜单绑定
        roleMenuService.deleteByRoleId(id);
        // 删除角色本身
        this.removeById(id);
    }

    @Override
    public void changeRoleStatus(SysRoleStatusDTO dto) {
        // 存在性检查
        SysRoleEntity roleEntity = this.getById(dto.getId());
        if (roleEntity == null) {
            throw new BusinessException(ROLE_NOT_FOUND, "被变更的角色不存在");
        }

        // 已是目标状态就直接返回
        if (roleEntity.getStatus().equals(dto.getStatus())) {
            return;
        }

        // 更新角色状态
        LambdaUpdateWrapper<SysRoleEntity> updateWrapper = new LambdaUpdateWrapper<SysRoleEntity>()
                .eq(SysRoleEntity::getId, roleEntity.getId())
                .set(SysRoleEntity::getStatus, dto.getStatus());

        boolean updated = this.update(new SysRoleEntity(), updateWrapper);
        if (!updated) {
            throw new BusinessException(ROLE_NOT_FOUND, "被变更的角色不存在");
        }
    }

    @Override
    public List<SysRoleListVO> getRoleList() {
        // 查询全量角色
        List<SysRoleEntity> roles = this.lambdaQuery()
                .orderByAsc(SysRoleEntity::getSort)
                .orderByAsc(SysRoleEntity::getId)
                .list();

        // 实体转换 VO
        return roles.stream()
                .map(SysRoleListVO::of)
                .collect(Collectors.toList());
    }
}
