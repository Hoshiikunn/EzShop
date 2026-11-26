package cn.hoshiikunn.ezshop.modules.sys.controller;

import cn.hoshiikunn.ezshop.common.annotation.OperLog;
import cn.hoshiikunn.ezshop.modules.sys.dto.SysProfilePasswordDTO;
import cn.hoshiikunn.ezshop.modules.sys.dto.SysProfileUsernameDTO;
import cn.hoshiikunn.ezshop.modules.sys.service.SysProfileService;
import cn.hoshiikunn.ezshop.modules.sys.vo.SysProfileVO;
import cn.hoshiikunn.ezshop.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 个人资料控制层
 */
@Tag(name = "个人中心")
@RestController
@RequestMapping("/admin/profile")
@RequiredArgsConstructor
public class SysProfileController {

    private final SysProfileService profileService;

    @GetMapping
    @Operation(summary = "个人资料")
    public Result<SysProfileVO> profile() {
        SysProfileVO profile = profileService.getProfile();

        return Result.ok(profile);
    }

    @GetMapping("/roles")
    @Operation(summary = "已绑定角色")
    public Result<List<String>> roles() {
        List<String> roles = profileService.getRoles();

        return Result.ok(roles);
    }

    @PutMapping("/change/password")
    @Operation(summary = "修改密码")
    @OperLog(module = "个人中心", operation = "修改密码")
    public Result<Void> changePassword(@Valid @RequestBody SysProfilePasswordDTO dto) {
        profileService.changePassword(dto);

        return Result.ok(null, "密码修改成功，请重新登录");
    }

    @PutMapping("/change/username")
    @Operation(summary = "修改用户名")
    @OperLog(module = "个人中心", operation = "修改用户名")
    public Result<Void> changeUsername(@Valid @RequestBody SysProfileUsernameDTO dto) {
        profileService.changeUsername(dto);

        return Result.ok(null, "用户名修改成功");
    }

    @GetMapping("/permissions")
    @Operation(summary = "权限标识")
    public Result<List<String>> permissions() {
        List<String> permissions = profileService.getPermissions();

        return Result.ok(permissions);
    }
}
