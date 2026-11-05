package cn.hoshiikunn.ezshop.modules.sys.service.impl;

import cn.hoshiikunn.ezshop.base.BaseServiceImpl;
import cn.hoshiikunn.ezshop.exception.BusinessException;
import cn.hoshiikunn.ezshop.modules.security.user.SecurityUser;
import cn.hoshiikunn.ezshop.modules.sys.dao.SysUserDao;
import cn.hoshiikunn.ezshop.modules.sys.dto.SysUserDTO;
import cn.hoshiikunn.ezshop.modules.sys.dto.SysUserPageDTO;
import cn.hoshiikunn.ezshop.modules.sys.entity.SysUserEntity;
import cn.hoshiikunn.ezshop.modules.sys.service.SysUserService;
import cn.hoshiikunn.ezshop.modules.sys.vo.SysUserInfoVO;
import cn.hoshiikunn.ezshop.modules.sys.vo.SysUserInitVO;
import cn.hoshiikunn.ezshop.modules.sys.vo.SysUserPageVO;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

import static cn.hoshiikunn.ezshop.common.result.AdminResultCode.*;
import static cn.hoshiikunn.ezshop.modules.sys.enums.IsSuperAdminEnum.IS_SUPER_ADMIN;
import static cn.hoshiikunn.ezshop.modules.sys.enums.IsSuperAdminEnum.NOT_SUPER_ADMIN;
import static cn.hoshiikunn.ezshop.modules.sys.enums.SysUserStatusEnum.ENABLE;

@Slf4j
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {

    @Override
    public SysUserEntity getUserEntityByUsername(String username) {
        LambdaQueryWrapper<SysUserEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUserEntity::getUsername, username);

        return this.getOne(wrapper);
    }

    @Override
    public void updateLastLoginInfo(String ip, Long userId) {
        LambdaUpdateWrapper<SysUserEntity> wrapper = new LambdaUpdateWrapper<SysUserEntity>()
                .eq(SysUserEntity::getId, userId)
                .set(SysUserEntity::getLastLoginIp, ip)
                .set(SysUserEntity::getLastLoginTime, new Date());

        this.update(wrapper);
    }

    @Override
    public Page<SysUserPageVO> pageUsers(SysUserPageDTO dto) {
        // 非超管无法查看超管账号
        SysUserEntity viewer = this.getById(SecurityUser.getUserId());
        boolean viewerIsSuper = viewer != null
                && IS_SUPER_ADMIN.getValue().equals(viewer.getIsSuperAdmin());

        Page<SysUserEntity> page = new Page<>(dto.getCurrent(), dto.getSize());

        LambdaQueryWrapper<SysUserEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(dto.getUsername()), SysUserEntity::getUsername, dto.getUsername())
                .like(StrUtil.isNotBlank(dto.getRealName()), SysUserEntity::getRealName, dto.getRealName())
                .eq(StrUtil.isNotBlank(dto.getMobile()), SysUserEntity::getMobile, dto.getMobile())
                .eq(dto.getGender() != null, SysUserEntity::getGender, dto.getGender())
                .eq(dto.getStatus() != null, SysUserEntity::getStatus, dto.getStatus())
                .ne(!viewerIsSuper, SysUserEntity::getIsSuperAdmin, IS_SUPER_ADMIN.getValue())
                .ge(dto.getCreateTimeBegin() != null, SysUserEntity::getCreateTime, dto.getCreateTimeBegin())
                .le(dto.getCreateTimeEnd() != null, SysUserEntity::getCreateTime, dto.getCreateTimeEnd())
                .orderByDesc(SysUserEntity::getCreateTime);
        Page<SysUserEntity> result = this.page(page, wrapper);

        return (Page<SysUserPageVO>) result.convert(SysUserPageVO::of);
    }

    @Override
    public SysUserInfoVO getUserInfo(Long id) {
        SysUserEntity userEntity = this.getById(id);
        if (userEntity == null) {
            throw new BusinessException(USER_NOT_FOUND);
        }

        // 非超管不可查看超管详情
        SysUserEntity viewer = this.getById(SecurityUser.getUserId());
        boolean viewerIsSuper = viewer != null
                && IS_SUPER_ADMIN.getValue().equals(viewer.getIsSuperAdmin());
        if (!viewerIsSuper && IS_SUPER_ADMIN.getValue().equals(userEntity.getIsSuperAdmin())) {
            throw new BusinessException(USER_NOT_FOUND);
        }

        return BeanUtil.toBean(userEntity, SysUserInfoVO.class);
    }

    @Override
    public SysUserInitVO addUser(SysUserDTO dto) {
        // 检查唯一性
        checkUnique(dto);
        // DTO转实体
        SysUserEntity userEntity = BeanUtil.copyProperties(dto, SysUserEntity.class);
        // 处理空串 统一为null
        userEntity.setMobile(StrUtil.blankToDefault(userEntity.getMobile(), null));
        userEntity.setEmail(StrUtil.blankToDefault(userEntity.getEmail(), null));
        userEntity.setAvatarUrl(StrUtil.blankToDefault(userEntity.getAvatarUrl(), null));
        // 填充字段与随机初始密码
        userEntity.setIsSuperAdmin(NOT_SUPER_ADMIN.getValue());
        userEntity.setStatus(ENABLE.getValue());
        String rawPassword = RandomUtil.randomString(6);
        userEntity.setPassword(BCrypt.hashpw(rawPassword));
        // 写库
        this.save(userEntity);

        // 响应初始用户信息
        SysUserInitVO vo = new SysUserInitVO();
        vo.setUsername(userEntity.getUsername());
        vo.setInitPassword(rawPassword);
        return vo;
    }

    @Override
    public void updateUser(SysUserDTO dto) {
        // 检查被修改的用户是否存在
        SysUserEntity userEntity = this.getById(dto.getId());
        if (userEntity == null) {
            throw new BusinessException(USER_NOT_FOUND, "被修改的用户不存在");
        }
        // 检查唯一性
        checkUnique(dto);
        // 组装修改内容
        LambdaUpdateWrapper<SysUserEntity> wrapper = new LambdaUpdateWrapper<SysUserEntity>()
                .eq(SysUserEntity::getId, userEntity.getId())
                .set(SysUserEntity::getUsername, dto.getUsername())
                .set(SysUserEntity::getRealName, dto.getRealName())
                .set(SysUserEntity::getGender, dto.getGender())
                .set(SysUserEntity::getMobile, StrUtil.blankToDefault(dto.getMobile(), null))
                .set(SysUserEntity::getEmail, StrUtil.blankToDefault(dto.getEmail(), null))
                .set(SysUserEntity::getAvatarUrl, StrUtil.blankToDefault(dto.getAvatarUrl(), null));
        // 写库
        boolean updated = this.update(new SysUserEntity(), wrapper);
        if (!updated) {
            throw new BusinessException(USER_NOT_FOUND, "被修改的用户不存在");
        }
    }

    /**
     * 检查唯一性
     * <p>
     * username、mobile、email 唯一字段
     *
     * @param dto 用户参数
     * @throws BusinessException 任一唯一字段冲突时抛出
     */
    private void checkUnique(SysUserDTO dto) {
        boolean isUpdate = dto.getId() != null;

        long usernameCount = this.count(new LambdaQueryWrapper<SysUserEntity>()
                .eq(SysUserEntity::getUsername, dto.getUsername())
                .ne(isUpdate, SysUserEntity::getId, dto.getId()));
        if (usernameCount > 0) {
            throw new BusinessException(USER_USERNAME_EXISTS);
        }

        // mobile、email 是可选字段，传了才查重
        if (StrUtil.isNotBlank(dto.getMobile())) {
            long mobileCount = this.count(new LambdaQueryWrapper<SysUserEntity>()
                    .eq(SysUserEntity::getMobile, dto.getMobile())
                    .ne(isUpdate, SysUserEntity::getId, dto.getId()));
            if (mobileCount > 0) {
                throw new BusinessException(USER_MOBILE_EXISTS);
            }
        }

        if (StrUtil.isNotBlank(dto.getEmail())) {
            long emailCount = this.count(new LambdaQueryWrapper<SysUserEntity>()
                    .eq(SysUserEntity::getEmail, dto.getEmail())
                    .ne(isUpdate, SysUserEntity::getId, dto.getId()));
            if (emailCount > 0) {
                throw new BusinessException(USER_EMAIL_EXISTS);
            }
        }
    }
}
