package cn.hoshiikunn.ezshop.modules.sys.service.impl;

import cn.hoshiikunn.ezshop.base.BaseServiceImpl;
import cn.hoshiikunn.ezshop.exception.BusinessException;
import cn.hoshiikunn.ezshop.modules.sys.dao.SysRoleDao;
import cn.hoshiikunn.ezshop.modules.sys.dto.SysRolePageDTO;
import cn.hoshiikunn.ezshop.modules.sys.entity.SysRoleEntity;
import cn.hoshiikunn.ezshop.modules.sys.service.SysRoleService;
import cn.hoshiikunn.ezshop.modules.sys.vo.SysRoleInfoVO;
import cn.hoshiikunn.ezshop.modules.sys.vo.SysRolePageVO;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;

import static cn.hoshiikunn.ezshop.common.result.AdminResultCode.ROLE_NOT_FOUND;

/**
 * 角色服务实现类
 */
@Service
public class SysRoleServiceImpl extends BaseServiceImpl<SysRoleDao, SysRoleEntity> implements SysRoleService {

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

        return BeanUtil.toBean(roleEntity, SysRoleInfoVO.class);
    }
}
