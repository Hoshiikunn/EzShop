package cn.hoshiikunn.ezshop.modules.sys.entity;

import cn.hoshiikunn.ezshop.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 登录日志实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_login_log")
public class SysLoginLogEntity extends BaseEntity {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 登录IP
     */
    private String ip;

    /**
     * 登录地点
     */
    private String loginLocation;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * 状态 0-失败 1-成功
     */
    private Integer status;

    /**
     * 结果描述
     */
    private String message;
}
