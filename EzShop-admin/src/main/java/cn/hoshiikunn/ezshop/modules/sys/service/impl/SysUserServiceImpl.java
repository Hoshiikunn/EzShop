package cn.hoshiikunn.ezshop.modules.sys.service.impl;

import cn.hoshiikunn.ezshop.base.BaseServiceImpl;
import cn.hoshiikunn.ezshop.modules.sys.dao.SysUserDao;
import cn.hoshiikunn.ezshop.modules.sys.entity.SysUserEntity;
import cn.hoshiikunn.ezshop.modules.sys.service.SysUserService;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {
}
