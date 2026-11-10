package cn.hoshiikunn.ezshop.modules.sys.controller;

import cn.hoshiikunn.ezshop.modules.sys.dto.SysRoleDTO;
import cn.hoshiikunn.ezshop.modules.sys.dto.SysRolePageDTO;
import cn.hoshiikunn.ezshop.modules.sys.service.SysRoleService;
import cn.hoshiikunn.ezshop.modules.sys.vo.SysRoleInfoVO;
import cn.hoshiikunn.ezshop.modules.sys.vo.SysRolePageVO;
import cn.hoshiikunn.ezshop.result.Result;
import cn.hoshiikunn.ezshop.utils.ValidatorUtils;
import cn.hoshiikunn.ezshop.validation.group.AddGroup;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

/**
 * 角色控制层
 */
@Tag(name = "角色管理")
@RestController
@RequestMapping("/admin/sys/role")
@RequiredArgsConstructor
public class SysRoleController {

    private final SysRoleService roleService;

    @GetMapping("/page")
    @Operation(summary = "分页条件查询")
    @RequiresPermissions("sys:role:page")
    public Result<Page<SysRolePageVO>> page(@ParameterObject SysRolePageDTO dto) {
        ValidatorUtils.validateEntity(dto);

        Page<SysRolePageVO> rolePage = roleService.pageRole(dto);

        return new Result<Page<SysRolePageVO>>().ok(rolePage);
    }

    @GetMapping("/{id}")
    @Operation(summary = "角色详情")
    @RequiresPermissions("sys:role:query")
    public Result<SysRoleInfoVO> info(@PathVariable Long id) {
        SysRoleInfoVO roleInfo = roleService.getRoleInfo(id);

        return new Result<SysRoleInfoVO>().ok(roleInfo);
    }

    @PostMapping("/add")
    @Operation(summary = "添加角色")
    @RequiresPermissions("sys:role:add")
    public Result<?> add(@RequestBody SysRoleDTO dto) {
        ValidatorUtils.validateEntity(dto, AddGroup.class);

        roleService.addRole(dto);

        return new Result<>().ok(null, "添加成功");
    }
}
