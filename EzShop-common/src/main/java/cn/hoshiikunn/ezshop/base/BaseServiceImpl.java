package cn.hoshiikunn.ezshop.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;

/**
 * 基础服务实现类
 * <p>
 * 所有服务实现类必须继承此类
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements BaseService<T> {
}
