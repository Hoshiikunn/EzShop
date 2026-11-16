package cn.hoshiikunn.ezshop.modules.sys.controller;

import cn.hoshiikunn.ezshop.modules.sys.dto.SysMenuDTO;
import cn.hoshiikunn.ezshop.modules.sys.service.SysMenuService;
import cn.hoshiikunn.ezshop.modules.sys.vo.SysMenuInfoVO;
import cn.hoshiikunn.ezshop.modules.sys.vo.SysMenuListVO;
import cn.hoshiikunn.ezshop.modules.sys.vo.SysMenuTreeVO;
import cn.hoshiikunn.ezshop.result.Result;
import cn.hoshiikunn.ezshop.utils.ValidatorUtils;
import cn.hoshiikunn.ezshop.validation.group.AddGroup;
import cn.hoshiikunn.ezshop.validation.group.UpdateGroup;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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

        return new Result<List<SysMenuTreeVO>>().ok(tree);
    }

    @GetMapping("/{id}")
    @Operation(summary = "菜单详情")
    @RequiresPermissions("sys:menu:info")
    public Result<SysMenuInfoVO> info(@PathVariable Long id) {
        SysMenuInfoVO vo = menuService.getMenuInfo(id);

        return new Result<SysMenuInfoVO>().ok(vo);
    }

    @GetMapping("/list")
    @Operation(summary = "菜单列表")
    @RequiresPermissions("sys:menu:list")
    public Result<List<SysMenuListVO>> list() {
        List<SysMenuListVO> menus = menuService.getMenuList();

        return new Result<List<SysMenuListVO>>().ok(menus);
    }

    @PostMapping("/add")
    @Operation(summary = "添加菜单")
    @RequiresPermissions("sys:menu:add")
    public Result<?> add(@RequestBody SysMenuDTO dto) {
        ValidatorUtils.validateEntity(dto, AddGroup.class);

        menuService.addMenu(dto);

        return new Result<>().ok(null, "添加成功");
    }

    @PutMapping("/update")
    @Operation(summary = "修改菜单")
    @RequiresPermissions("sys:menu:update")
    public Result<?> update(@RequestBody SysMenuDTO dto) {
        ValidatorUtils.validateEntity(dto, UpdateGroup.class);

        menuService.updateMenu(dto);

        return new Result<>().ok(null, "修改成功");
    }
}
