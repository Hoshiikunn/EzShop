package cn.hoshiikunn.ezshop.modules.sys.service.impl;

import cn.hoshiikunn.ezshop.exception.BusinessException;
import cn.hoshiikunn.ezshop.modules.security.user.SecurityUser;
import cn.hoshiikunn.ezshop.modules.sys.entity.SysUserEntity;
import cn.hoshiikunn.ezshop.modules.sys.service.SysProfileService;
import cn.hoshiikunn.ezshop.modules.sys.service.SysUserService;
import cn.hoshiikunn.ezshop.modules.sys.vo.SysProfileVO;
import cn.hutool.core.bean.BeanUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static cn.hoshiikunn.ezshop.common.result.AdminResultCode.USER_NOT_FOUND;
import static cn.hoshiikunn.ezshop.result.ResultCode.UNAUTHORIZED;

/**
 * 个人资料业务实现类
 */
@Service
@RequiredArgsConstructor
public class SysProfileServiceImpl implements SysProfileService {

    private final SysUserService userService;

    @Override
    public SysProfileVO getProfile() {
        SysUserEntity user = userService.getById(currentUserId());
        if (user == null) {
            throw new BusinessException(USER_NOT_FOUND);
        }

        return BeanUtil.toBean(user, SysProfileVO.class);
    }

    /**
     * 当前用户ID
     *
     * @return 用户ID
     */
    private Long currentUserId() {
        Long userId = SecurityUser.getUserId();
        if (userId == null) {
            throw new BusinessException(UNAUTHORIZED);
        }

        return userId;
    }
}
