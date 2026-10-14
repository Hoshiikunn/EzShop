package cn.hoshiikunn.ezshop.common.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 字段自动填充处理器
 */
@Component
public class FieldMetaObjectHandler implements MetaObjectHandler {
    private final static String CREATE_TIME = "createTime";
    private final static String CREATOR = "creator";
    private final static String UPDATE_TIME = "updateTime";
    private final static String UPDATER = "updater";

    @Override
    public void insertFill(MetaObject metaObject) {
        // TODO: 需要获取用户ID，先完成 登录功能 与 shiro认证
        // UserDetail user = SecurityUser.getUser();
        Date date = new Date();

        // 创建者
        // strictInsertFill(metaObject, CREATOR, Long.class, user.getId());
        // 创建时间
        strictInsertFill(metaObject, CREATE_TIME, Date.class, date);
        // 更新者
        // strictInsertFill(metaObject, UPDATER, Long.class, user.getId());
        // 更新时间
        strictInsertFill(metaObject, UPDATE_TIME, Date.class, date);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // TODO: 同上
        // UserDetail user = SecurityUser.getUser();

        // 更新者
        // strictUpdateFill(metaObject, UPDATER, Long.class, user.getId());
        // 更新时间
        strictUpdateFill(metaObject, UPDATE_TIME, Date.class, new Date());
    }
}
