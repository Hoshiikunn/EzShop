package cn.hoshiikunn.ezshop.modules.sys.controller;

import cn.hoshiikunn.ezshop.modules.sys.service.SysProfileService;
import cn.hoshiikunn.ezshop.modules.sys.vo.SysProfileVO;
import cn.hoshiikunn.ezshop.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
