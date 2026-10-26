package cn.hoshiikunn.ezshop.modules.sys.vo;

import cn.hoshiikunn.ezshop.modules.sys.entity.SysUserEntity;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * 系统用户分页信息
 */
@Data
@Schema(description = "系统用户分页信息")
public class SysUserPageVO {

    @Schema(description = "用户ID")
    private Long id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "真实姓名")
    private String realName;

    @Schema(description = "头像 Url")
    private String avatarUrl;

    @Schema(description = "性别 0-女 1-男")
    private Integer gender;

    @Schema(description = "手机号")
    private String mobile;

    @Schema(description = "状态 0-禁用 1-启用")
    private Integer status;

    @Schema(description = "创建时间")
    private Date createTime;

    /**
     * 实体转 VO
     */
    public static SysUserPageVO of(SysUserEntity entity) {
        return BeanUtil.toBean(entity, SysUserPageVO.class);
    }
}
