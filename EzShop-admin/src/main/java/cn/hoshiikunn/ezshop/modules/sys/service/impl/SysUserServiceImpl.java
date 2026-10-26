package cn.hoshiikunn.ezshop.modules.sys.service.impl;

import cn.hoshiikunn.ezshop.base.BaseServiceImpl;
import cn.hoshiikunn.ezshop.exception.BusinessException;
import cn.hoshiikunn.ezshop.modules.sys.dao.SysUserDao;
import cn.hoshiikunn.ezshop.modules.sys.dto.SysUserPageDTO;
import cn.hoshiikunn.ezshop.modules.sys.entity.SysUserEntity;
import cn.hoshiikunn.ezshop.modules.sys.service.SysUserService;
import cn.hoshiikunn.ezshop.modules.sys.vo.SysUserInfoVO;
import cn.hoshiikunn.ezshop.modules.sys.vo.SysUserPageVO;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

import static cn.hoshiikunn.ezshop.result.ResultCode.USER_NOT_FOUND;

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
        Page<SysUserEntity> page = new Page<>(dto.getCurrent(), dto.getSize());

        LambdaQueryWrapper<SysUserEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(dto.getUsername()), SysUserEntity::getUsername, dto.getUsername())
                .like(StrUtil.isNotBlank(dto.getRealName()), SysUserEntity::getRealName, dto.getRealName())
                .eq(StrUtil.isNotBlank(dto.getMobile()), SysUserEntity::getMobile, dto.getMobile())
                .eq(dto.getStatus() != null, SysUserEntity::getStatus, dto.getStatus())
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

        return BeanUtil.toBean(userEntity, SysUserInfoVO.class);
    }
}
