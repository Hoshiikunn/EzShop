package cn.hoshiikunn.ezshop.common.result;

import cn.hoshiikunn.ezshop.result.IResultCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 后台管理业务响应码
 *
 * <h5>模块号段登记</h5>
 * <ul>
 *     <li>101 - 认证模块 </li>
 *     <li>102 - 用户模块 </li>
 *     <li>103 - 角色模块 </li>
 *     <li>104 - 菜单模块 </li>
 * </ul>
 */
@Getter
@AllArgsConstructor
public enum AdminResultCode implements IResultCode {

    // ==================== 认证模块 - 101 ====================
    USERNAME_OR_PASSWORD_ERROR(101001, "用户名或密码错误"),

    // ==================== 用户模块 - 102 ====================
    USER_NOT_FOUND(102001, "用户不存在"),
    USER_USERNAME_EXISTS(102002, "用户名已存在"),
    USER_MOBILE_EXISTS(102003, "该手机号已被使用"),
    USER_EMAIL_EXISTS(102004, "该邮箱已被使用"),
    USER_SUPER_ADMIN_DELETE_FORBIDDEN(102005, "禁止删除超级管理员"),
    USER_SELF_DELETE_FORBIDDEN(102006, "不能删除当前登录账号"),
    USER_SUPER_ADMIN_DISABLE_FORBIDDEN(102007, "不可禁用超级管理员"),
    USER_SELF_DISABLE_FORBIDDEN(102008, "不可禁用自己"),
    USER_DISABLED(102009, "账号已被禁用"),

    // ==================== 角色模块 - 103 ====================
    ROLE_NOT_FOUND(103001, "角色不存在"),
    ROLE_KEY_EXISTS(103002, "权限字符已存在"),
    ROLE_KEY_NOT_MODIFIABLE(103003, "权限字符不可被修改"),
    ROLE_HAS_USERS(103004, "角色已绑定用户，禁止直接删除"),
    ROLE_ID_REQUIRED(103005, "角色ID不能为空"),


    // ==================== 菜单模块 - 104 ====================
    MENU_NOT_FOUND(104001, "菜单不存在"),
    MENU_ID_REQUIRED(104002, "菜单ID不能为空"),
    MENU_NAME_EXISTS(104003, "同级菜单名称已存在"),
    MENU_TYPE_MISMATCH(104004, "菜单类型与挂载位置不匹配"),
    MENU_PERMS_REQUIRED(104005, "菜单权限标识为必填项"),
    MENU_PERMS_NOT_ALLOWED(104006, "菜单不可设置权限标识"),
    MENU_TYPE_NOT_MODIFIABLE(104007, "菜单类型不可修改"),
    MENU_PARENT_CYCLE(104008, "上级菜单不能选择自身或自身的下级菜单"),
    MENU_HAS_CHILDREN(104009, "存在子菜单，不可删除"),
    MENU_VISIBLE_REQUIRED(104010, "菜单可见性为必填项"),
    MENU_OPEN_STYLE_REQUIRED(104011, "菜单打开方式为必填项"),
    MENU_PATH_NOT_ALLOWED(104012, "菜单不可设置路由地址"),
    MENU_COMPONENT_NOT_ALLOWED(104013, "菜单不可设置组件路径"),
    MENU_VISIBLE_NOT_ALLOWED(104014, "菜单不可设置可见性"),
    MENU_OPEN_STYLE_NOT_ALLOWED(104015, "菜单不可设置打开方式"),
    ;

    // 响应码
    private final Integer code;
    // 消息内容
    private final String msg;
}
