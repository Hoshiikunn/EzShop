package cn.hoshiikunn.ezshop.modules.sys.controller;

import cn.hoshiikunn.ezshop.modules.sys.dto.SysLoginLogPageDTO;
import cn.hoshiikunn.ezshop.modules.sys.dto.SysOperLogPageDTO;
import cn.hoshiikunn.ezshop.modules.sys.service.SysLoginLogService;
import cn.hoshiikunn.ezshop.modules.sys.service.SysOperLogService;
import cn.hoshiikunn.ezshop.modules.sys.vo.SysLoginLogPageVO;
import cn.hoshiikunn.ezshop.modules.sys.vo.SysOperLogPageVO;
import cn.hoshiikunn.ezshop.result.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * 日志控制层
 */
@Tag(name = "日志管理")
@RestController
@RequestMapping("/admin/sys/log")
@RequiredArgsConstructor
public class SysLogController {

    private final SysLoginLogService loginLogService;
    private final SysOperLogService operLogService;

    @GetMapping("/login/page")
    @Operation(summary = "登录日志分页条件查询")
    @RequiresPermissions("sys:log:login:query")
    public Result<Page<SysLoginLogPageVO>> loginPage(@Validated @ParameterObject SysLoginLogPageDTO dto) {
        Page<SysLoginLogPageVO> loginLogPage = loginLogService.pageLoginLog(dto);

        return Result.ok(loginLogPage);
    }

    @GetMapping("/oper/page")
    @Operation(summary = "操作日志分页条件查询")
    @RequiresPermissions("sys:log:oper:query")
    public Result<Page<SysOperLogPageVO>> operPage(@Validated @ParameterObject SysOperLogPageDTO dto) {
        Page<SysOperLogPageVO> operLogPage = operLogService.pageOperLog(dto);

        return Result.ok(operLogPage);
    }

    @PostMapping("/login/batch/delete")
    @Operation(summary = "批量删除登录日志")
    @RequiresPermissions("sys:log:login:delete")
    public Result<Void> batchDeleteLoginLog(@RequestBody Set<Long> logIds) {
        loginLogService.batchDeleteLoginLogs(logIds);

        return Result.ok(null, "删除成功");
    }

    @PostMapping("/oper/batch/delete")
    @Operation(summary = "批量删除操作日志")
    @RequiresPermissions("sys:log:oper:delete")
    public Result<Void> batchDeleteOperLog(@RequestBody Set<Long> operIds) {
        operLogService.batchDeleteOperLogs(operIds);

        return Result.ok(null, "删除成功");
    }
}
