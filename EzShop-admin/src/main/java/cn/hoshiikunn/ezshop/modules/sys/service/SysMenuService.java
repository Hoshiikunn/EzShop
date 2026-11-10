package cn.hoshiikunn.ezshop.modules.sys.service;

import cn.hoshiikunn.ezshop.base.BaseService;
import cn.hoshiikunn.ezshop.modules.sys.entity.SysMenuEntity;
import cn.hoshiikunn.ezshop.modules.sys.vo.SysMenuTreeVO;

import java.util.List;

/**
 * 菜单服务层
 */
public interface SysMenuService extends BaseService<SysMenuEntity> {

    /**
     * 获取菜单树
     *
     * @return 菜单树
     */
    List<SysMenuTreeVO> getMenuTree();
}
