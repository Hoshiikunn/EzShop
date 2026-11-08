package cn.hoshiikunn.ezshop.modules.sys.service;

import cn.hoshiikunn.ezshop.base.BaseService;
import cn.hoshiikunn.ezshop.modules.sys.dto.SysRolePageDTO;
import cn.hoshiikunn.ezshop.modules.sys.entity.SysRoleEntity;
import cn.hoshiikunn.ezshop.modules.sys.vo.SysRoleInfoVO;
import cn.hoshiikunn.ezshop.modules.sys.vo.SysRolePageVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 角色服务层
 */
public interface SysRoleService extends BaseService<SysRoleEntity> {

    /**
     * 角色分页查询
     *
     * @param dto 角色分页条件查询参数
     * @return 查询的分页对象
     */
    Page<SysRolePageVO> pageRole(SysRolePageDTO dto);

    /**
     * 获取角色详情
     *
     * @param id 角色ID
     * @return 角色信息
     */
    SysRoleInfoVO getRoleInfo(Long id);
}
