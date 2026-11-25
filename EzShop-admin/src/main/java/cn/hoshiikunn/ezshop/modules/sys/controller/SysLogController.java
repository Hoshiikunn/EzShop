package cn.hoshiikunn.ezshop.modules.sys.controller;

import cn.hoshiikunn.ezshop.modules.sys.dto.SysLoginLogPageDTO;
import cn.hoshiikunn.ezshop.modules.sys.service.SysLoginLogService;
import cn.hoshiikunn.ezshop.modules.sys.vo.SysLoginLogPageVO;
import cn.hoshiikunn.ezshop.result.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 日志控制层
 */
@Tag(name = "日志管理")
@RestController
@RequestMapping("/admin/sys/log")
@RequiredArgsConstructor
public class SysLogController {

    private final SysLoginLogService loginLogService;

    @GetMapping("/login/page")
    @Operation(summary = "分页条件查询")
    @RequiresPermissions("sys:log:login:query")
    public Result<Page<SysLoginLogPageVO>> loginPage(@Validated @ParameterObject SysLoginLogPageDTO dto) {
        Page<SysLoginLogPageVO> loginLogPage = loginLogService.pageLoginLog(dto);

        return Result.ok(loginLogPage);
    }
}
