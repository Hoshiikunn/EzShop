package cn.hoshiikunn.ezshop.common.handler;

import cn.hoshiikunn.ezshop.modules.security.user.SecurityUser;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 字段自动填充处理器
 * <p>
 * 进行 新增与更新 写库操作时，会自动填充字段值
 */
@Component
public class FieldMetaObjectHandler implements MetaObjectHandler {
    private final static String CREATE_TIME = "createTime";
    private final static String CREATOR = "creator";
    private final static String UPDATE_TIME = "updateTime";
    private final static String UPDATER = "updater";

    @Override
    public void insertFill(MetaObject metaObject) {
        Long userId = SecurityUser.getUserId();

        // 创建者
        strictInsertFill(metaObject, CREATOR, Long.class, userId);
        // 创建时间
        strictInsertFill(metaObject, CREATE_TIME, Date.class, new Date());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Long userId = SecurityUser.getUserId();

        // 更新者
        strictUpdateFill(metaObject, UPDATER, Long.class, userId);
        // 更新时间
        strictUpdateFill(metaObject, UPDATE_TIME, Date.class, new Date());
    }
}
