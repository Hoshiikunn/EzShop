package cn.hoshiikunn.ezshop.modules.sys.controller;

import cn.hoshiikunn.ezshop.modules.sys.dto.SysMenuDTO;
import cn.hoshiikunn.ezshop.modules.sys.service.SysMenuService;
import cn.hoshiikunn.ezshop.modules.sys.vo.SysMenuInfoVO;
import cn.hoshiikunn.ezshop.modules.sys.vo.SysMenuListVO;
import cn.hoshiikunn.ezshop.modules.sys.vo.SysMenuTreeVO;
import cn.hoshiikunn.ezshop.result.Result;
import cn.hoshiikunn.ezshop.validation.group.AddGroup;
import cn.hoshiikunn.ezshop.validation.group.UpdateGroup;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

        return Result.ok(tree);
    }

    @GetMapping("/{id}")
    @Operation(summary = "菜单详情")
    @RequiresPermissions("sys:menu:info")
    public Result<SysMenuInfoVO> info(@PathVariable Long id) {
        SysMenuInfoVO vo = menuService.getMenuInfo(id);

        return Result.ok(vo);
    }

    @GetMapping("/list")
    @Operation(summary = "菜单列表")
    @RequiresPermissions("sys:menu:list")
    public Result<List<SysMenuListVO>> list() {
        List<SysMenuListVO> menus = menuService.getMenuList();

        return Result.ok(menus);
    }

    @PostMapping("/add")
    @Operation(summary = "添加菜单")
    @RequiresPermissions("sys:menu:add")
    public Result<Void> add(@Validated(AddGroup.class) @RequestBody SysMenuDTO dto) {
        menuService.addMenu(dto);

        return Result.ok(null, "添加成功");
    }

    @PutMapping("/update")
    @Operation(summary = "修改菜单")
    @RequiresPermissions("sys:menu:update")
    public Result<Void> update(@Validated(UpdateGroup.class) @RequestBody SysMenuDTO dto) {
        menuService.updateMenu(dto);

        return Result.ok(null, "修改成功");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除菜单")
    @RequiresPermissions("sys:menu:delete")
    public Result<Void> delete(@PathVariable Long id) {
        menuService.deleteMenu(id);

        return Result.ok(null, "删除成功");
    }
}
