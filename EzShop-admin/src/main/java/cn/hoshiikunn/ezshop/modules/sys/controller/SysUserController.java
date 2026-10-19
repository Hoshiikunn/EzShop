package cn.hoshiikunn.ezshop.modules.sys.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统用户控制层
 */
@Tag(name = "用户管理")
@RestController
@RequestMapping("/admin/sys/user")
@RequiredArgsConstructor
public class SysUserController {
}
