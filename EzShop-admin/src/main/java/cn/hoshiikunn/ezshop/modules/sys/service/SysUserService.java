package cn.hoshiikunn.ezshop.modules.sys.service;

import cn.hoshiikunn.ezshop.base.BaseService;
import cn.hoshiikunn.ezshop.modules.sys.dto.SysUserPageDTO;
import cn.hoshiikunn.ezshop.modules.sys.entity.SysUserEntity;
import cn.hoshiikunn.ezshop.modules.sys.vo.SysUserInfoVO;
import cn.hoshiikunn.ezshop.modules.sys.vo.SysUserPageVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 系统用户服务层
 */
public interface SysUserService extends BaseService<SysUserEntity> {

    /**
     * 根据用户名获取用户实体
     *
     * @param username 用户名
     * @return 系统用户实体
     */
    SysUserEntity getUserEntityByUsername(String username);

    /**
     * 更新最后登录时间
     *
     * @param ip     登录IP
     * @param userId 用户ID
     */
    void updateLastLoginInfo(String ip, Long userId);

    /**
     * 用户分页查询
     *
     * @param dto 系统用户分页条件查询参数
     * @return 查询的分页对象
     */
    Page<SysUserPageVO> pageUsers(SysUserPageDTO dto);

    /**
     * 获取指定用户详情
     *
     * @param id 目标用户ID
     * @return 用户信息
     */
    SysUserInfoVO getUserInfo(Long id);
}
