package cn.hoshiikunn.ezshop.modules.sys.controller;

import cn.hoshiikunn.ezshop.modules.sys.dto.SysUserBatchDeleteDTO;
import cn.hoshiikunn.ezshop.modules.sys.dto.SysUserDTO;
import cn.hoshiikunn.ezshop.modules.sys.dto.SysUserPageDTO;
import cn.hoshiikunn.ezshop.modules.sys.dto.SysUserStatusDTO;
import cn.hoshiikunn.ezshop.modules.sys.service.SysUserService;
import cn.hoshiikunn.ezshop.modules.sys.vo.SysUserInfoVO;
import cn.hoshiikunn.ezshop.modules.sys.vo.SysUserInitVO;
import cn.hoshiikunn.ezshop.modules.sys.vo.SysUserPageVO;
import cn.hoshiikunn.ezshop.modules.sys.vo.SysUserResetPasswordVO;
import cn.hoshiikunn.ezshop.result.Result;
import cn.hoshiikunn.ezshop.validation.group.AddGroup;
import cn.hoshiikunn.ezshop.validation.group.UpdateGroup;
import javax.validation.Valid;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 系统用户控制层
 */
@Tag(name = "系统用户管理")
@RestController
@RequestMapping("/admin/sys/user")
@RequiredArgsConstructor
public class SysUserController {

    private final SysUserService userService;

    @GetMapping("/page")
    @Operation(summary = "分页条件查询")
    @RequiresPermissions("sys:user:query")
    public Result<Page<SysUserPageVO>> page(@Validated @ParameterObject SysUserPageDTO dto) {
        Page<SysUserPageVO> userPage = userService.pageUsers(dto);

        return Result.ok(userPage);
    }

    @GetMapping("/{id}")
    @Operation(summary = "用户详情")
    @RequiresPermissions("sys:user:info")
    public Result<SysUserInfoVO> info(@PathVariable Long id) {
        SysUserInfoVO userInfo = userService.getUserInfo(id);

        return Result.ok(userInfo);
    }

    @PostMapping("/add")
    @Operation(summary = "添加用户")
    @RequiresPermissions("sys:user:add")
    public Result<SysUserInitVO> add(@Validated(AddGroup.class) @RequestBody SysUserDTO dto) {
        SysUserInitVO vo = userService.addUser(dto);

        return Result.ok(vo, "添加成功");
    }

    @PutMapping("/update")
    @Operation(summary = "修改用户")
    @RequiresPermissions("sys:user:update")
    public Result<Void> update(@Validated(UpdateGroup.class) @RequestBody SysUserDTO dto) {
        userService.updateUser(dto);

        return Result.ok(null, "修改成功");
    }

    @PostMapping("/batch/delete")
    @Operation(summary = "批量删除用户")
    @RequiresPermissions("sys:user:delete")
    public Result<Void> batchDelete(@Valid @RequestBody SysUserBatchDeleteDTO dto) {
        userService.batchDeleteUsers(dto.getIds());

        return Result.ok(null, "删除成功");
    }

    @PostMapping("/change/status")
    @Operation(summary = "变更用户状态")
    @RequiresPermissions("sys:user:status")
    public Result<Void> changeStatus(@Valid @RequestBody SysUserStatusDTO dto) {
        userService.changeUserStatus(dto);

        return Result.ok(null, "变更成功");
    }

    @PostMapping("/{id}/reset/password")
    @Operation(summary = "重置用户密码")
    @RequiresPermissions("sys:user:reset-password")
    public Result<SysUserResetPasswordVO> resetPassword(@PathVariable Long id) {
        SysUserResetPasswordVO vo = userService.resetPassword(id);

        return Result.ok(vo, "重置成功");
    }
}
