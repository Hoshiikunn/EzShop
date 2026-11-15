package cn.hoshiikunn.ezshop.modules.sys.service;

import cn.hoshiikunn.ezshop.base.BaseService;
import cn.hoshiikunn.ezshop.modules.sys.dto.SysMenuDTO;
import cn.hoshiikunn.ezshop.modules.sys.entity.SysMenuEntity;
import cn.hoshiikunn.ezshop.modules.sys.vo.SysMenuInfoVO;
import cn.hoshiikunn.ezshop.modules.sys.vo.SysMenuListVO;
import cn.hoshiikunn.ezshop.modules.sys.vo.SysMenuTreeVO;

import java.util.List;

/**
 * 菜单服务层
 */
public interface SysMenuService extends BaseService<SysMenuEntity> {

    /**
     * 获取全量菜单树
     *
     * @return 全量菜单树
     */
    List<SysMenuTreeVO> getMenuTree();

    /**
     * 获取菜单详情
     *
     * @param menuId 菜单ID
     * @return 菜单信息
     */
    SysMenuInfoVO getMenuInfo(Long menuId);

    /**
     * 获取菜单列表
     *
     * @return 菜单列表
     */
    List<SysMenuListVO> getMenuList();

    /**
     * 添加菜单
     *
     * @param dto 菜单参数
     */
    void addMenu(SysMenuDTO dto);
}
