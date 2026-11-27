package cn.hoshiikunn.ezshop.modules.sys.entity;

import cn.hoshiikunn.ezshop.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 操作日志实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_oper_log")
public class SysOperLogEntity extends BaseEntity {

    /**
     * 所属模块
     */
    private String module;

    /**
     * 操作内容
     */
    private String operation;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 操作人员
     */
    private String username;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 请求地址
     */
    private String requestUri;

    /**
     * 操作IP
     */
    private String ip;

    /**
     * 操作地点
     */
    private String operLocation;

    /**
     * 请求参数
     */
    private String requestParams;

    /**
     * 状态 0-失败 1-成功
     */
    private Integer status;

    /**
     * 失败原因
     */
    private String errorMsg;

    /**
     * 耗时 (毫秒)
     */
    private Integer elapsedMs;
}
