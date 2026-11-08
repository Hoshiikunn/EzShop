package cn.hoshiikunn.ezshop.modules.sys.entity;

import cn.hoshiikunn.ezshop.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_role")
public class SysRoleEntity extends BaseEntity {

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色权限键
     */
    private String roleKey;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 状态 0-禁用 1-启用
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;
}
