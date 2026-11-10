package cn.hoshiikunn.ezshop.modules.sys.controller;

import cn.hoshiikunn.ezshop.modules.sys.service.SysMenuService;
import cn.hoshiikunn.ezshop.modules.sys.vo.SysMenuTreeVO;
import cn.hoshiikunn.ezshop.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 菜单控制层
 */
@Tag(name = "菜单管理")
@RestController
@RequestMapping("/admin/sys/menu")
@RequiredArgsConstructor
public class SysMenuController {

    private final SysMenuService menuService;

    @GetMapping("/tree")
    @Operation(summary = "菜单树")
    @RequiresPermissions("sys:menu:tree")
    public Result<List<SysMenuTreeVO>> tree() {
        List<SysMenuTreeVO> tree = menuService.getMenuTree();

        return new Result<List<SysMenuTreeVO>>().ok(tree);
    }
}
