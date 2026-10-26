package cn.hoshiikunn.ezshop.modules.sys.controller;

import cn.hoshiikunn.ezshop.modules.sys.dto.SysUserPageDTO;
import cn.hoshiikunn.ezshop.modules.sys.service.SysUserService;
import cn.hoshiikunn.ezshop.modules.sys.vo.SysUserInfoVO;
import cn.hoshiikunn.ezshop.modules.sys.vo.SysUserPageVO;
import cn.hoshiikunn.ezshop.result.Result;
import cn.hoshiikunn.ezshop.utils.ValidatorUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Result<Page<SysUserPageVO>> page(@ParameterObject SysUserPageDTO dto) {
        ValidatorUtils.validateEntity(dto);

        Page<SysUserPageVO> userPage = userService.pageUsers(dto);

        return new Result<Page<SysUserPageVO>>().ok(userPage);
    }

    @GetMapping("/{id}")
    @Operation(summary = "用户详情")
    public Result<SysUserInfoVO> info(@PathVariable Long id) {
        SysUserInfoVO userInfo = userService.getUserInfo(id);

        return new Result<SysUserInfoVO>().ok(userInfo);
    }
}
