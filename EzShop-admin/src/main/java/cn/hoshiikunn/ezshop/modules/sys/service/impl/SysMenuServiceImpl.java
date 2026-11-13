package cn.hoshiikunn.ezshop.modules.sys.service.impl;

import cn.hoshiikunn.ezshop.base.BaseServiceImpl;
import cn.hoshiikunn.ezshop.modules.sys.dao.SysMenuDao;
import cn.hoshiikunn.ezshop.modules.sys.entity.SysMenuEntity;
import cn.hoshiikunn.ezshop.modules.sys.service.SysMenuService;
import cn.hoshiikunn.ezshop.modules.sys.vo.SysMenuTreeVO;
import cn.hoshiikunn.ezshop.utils.TreeUtils;
import cn.hutool.core.bean.BeanUtil;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static cn.hoshiikunn.ezshop.common.enums.CommonStatusEnum.ENABLE;

/**
 * 菜单服务实现类
 */
@Service
public class SysMenuServiceImpl extends BaseServiceImpl<SysMenuDao, SysMenuEntity> implements SysMenuService {

    @Override
    public List<SysMenuTreeVO> getMenuTree() {
        // 查询全量菜单
        List<SysMenuEntity> menus = this.lambdaQuery()
                .orderByAsc(SysMenuEntity::getSort)
                .orderByAsc(SysMenuEntity::getId)
                .list();

        // 实体转换 VO
        List<SysMenuTreeVO> nodes = menus.stream()
                .map(menu -> BeanUtil.toBean(menu, SysMenuTreeVO.class))
                .collect(Collectors.toList());

        // 组装全量菜单树
        return TreeUtils.build(
                nodes,
                SysMenuTreeVO::getId,
                SysMenuTreeVO::getPid,
                (parent, child) -> parent.getChildren().add(child),
                pid -> pid == null || pid == 0L,
                Comparator.comparing(SysMenuTreeVO::getSort)
                        .thenComparing(SysMenuTreeVO::getId)
        );
    }
}
