package cn.hoshiikunn.ezshop.modules.sys.service;

import cn.hoshiikunn.ezshop.base.BaseService;
import cn.hoshiikunn.ezshop.modules.sys.dto.SysRoleDTO;
import cn.hoshiikunn.ezshop.modules.sys.dto.SysRolePageDTO;
import cn.hoshiikunn.ezshop.modules.sys.dto.SysRoleStatusDTO;
import cn.hoshiikunn.ezshop.modules.sys.entity.SysRoleEntity;
import cn.hoshiikunn.ezshop.modules.sys.vo.SysRoleInfoVO;
import cn.hoshiikunn.ezshop.modules.sys.vo.SysRoleListVO;
import cn.hoshiikunn.ezshop.modules.sys.vo.SysRolePageVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

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

    /**
     * 添加角色
     *
     * @param dto 角色参数
     */
    void addRole(SysRoleDTO dto);

    /**
     * 修改角色
     *
     * @param dto 角色参数
     */
    void updateRole(SysRoleDTO dto);

    /**
     * 删除角色
     *
     * @param id    角色ID
     * @param force 是否强制删除仍被用户绑定的角色
     */
    void deleteRole(Long id, boolean force);

    /**
     * 变更角色状态
     *
     * @param dto 角色状态参数
     */
    void changeRoleStatus(SysRoleStatusDTO dto);

    /**
     * 获取全量角色列表
     *
     * @return 角色列表
     */
    List<SysRoleListVO> getRoleList();
}
