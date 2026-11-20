package cn.hoshiikunn.ezshop.modules.sys.controller;

import cn.hoshiikunn.ezshop.modules.sys.dto.LoginDTO;
import cn.hoshiikunn.ezshop.modules.sys.service.SysAuthService;
import cn.hoshiikunn.ezshop.modules.sys.vo.LoginTokenVO;
import cn.hoshiikunn.ezshop.modules.sys.vo.SysMenuTreeVO;
import cn.hoshiikunn.ezshop.result.Result;
import javax.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 系统认证控制层
 */
@Tag(name = "系统认证")
@RestController
@RequestMapping("/admin/auth")
@RequiredArgsConstructor
public class SysAuthController {

    private final SysAuthService authService;

    @PostMapping("/login")
    @Operation(summary = "登录")
    public Result<LoginTokenVO> login(@Valid @RequestBody LoginDTO dto, HttpServletRequest request) {
        LoginTokenVO loginToken = authService.login(request, dto);

        return Result.ok(loginToken, "登录成功");
    }

    @PostMapping("/logout")
    @Operation(summary = "登出")
    public Result<Void> logout() {
        authService.logout();

        return Result.ok(null, "已退出登录");
    }

    @GetMapping("/nav")
    @Operation(summary = "导航树")
    public Result<List<SysMenuTreeVO>> nav() {
        List<SysMenuTreeVO> navTree = authService.getNavMenus();

        return Result.ok(navTree);
    }
}
