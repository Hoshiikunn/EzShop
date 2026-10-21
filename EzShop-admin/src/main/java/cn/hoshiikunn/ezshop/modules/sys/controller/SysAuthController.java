package cn.hoshiikunn.ezshop.modules.sys.controller;

import cn.hoshiikunn.ezshop.modules.sys.dto.LoginDTO;
import cn.hoshiikunn.ezshop.modules.sys.service.SysAuthService;
import cn.hoshiikunn.ezshop.modules.sys.vo.LoginTokenVO;
import cn.hoshiikunn.ezshop.result.Result;
import cn.hoshiikunn.ezshop.utils.ValidatorUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Result<LoginTokenVO> login(@RequestBody LoginDTO dto) {
        ValidatorUtils.validateEntity(dto);

        LoginTokenVO loginToken = authService.login(dto);

        return new Result<LoginTokenVO>().ok(loginToken, "登录成功");
    }
}
