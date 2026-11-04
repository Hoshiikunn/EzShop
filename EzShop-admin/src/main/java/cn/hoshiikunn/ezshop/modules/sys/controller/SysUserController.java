package cn.hoshiikunn.ezshop.modules.sys.controller;

import cn.hoshiikunn.ezshop.modules.sys.dto.SysUserDTO;
import cn.hoshiikunn.ezshop.modules.sys.dto.SysUserPageDTO;
import cn.hoshiikunn.ezshop.modules.sys.service.SysUserService;
import cn.hoshiikunn.ezshop.modules.sys.vo.SysUserInfoVO;
import cn.hoshiikunn.ezshop.modules.sys.vo.SysUserInitVO;
import cn.hoshiikunn.ezshop.modules.sys.vo.SysUserPageVO;
import cn.hoshiikunn.ezshop.result.Result;
import cn.hoshiikunn.ezshop.utils.ValidatorUtils;
import cn.hoshiikunn.ezshop.validation.group.AddGroup;
import cn.hoshiikunn.ezshop.validation.group.UpdateGroup;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
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

    @PostMapping("/add")
    @Operation(summary = "添加用户")
    public Result<SysUserInitVO> add(@RequestBody SysUserDTO dto) {
        ValidatorUtils.validateEntity(dto, AddGroup.class);

        SysUserInitVO vo = userService.addUser(dto);

        return new Result<SysUserInitVO>().ok(vo, "添加成功");
    }

    @PutMapping("/update")
    @Operation(summary = "修改用户")
    public Result<?> update(@RequestBody SysUserDTO dto) {
        ValidatorUtils.validateEntity(dto, UpdateGroup.class);

        userService.updateUser(dto);

        return new Result<>().ok(null, "修改成功");
    }
}
