package cn.hoshiikunn.ezshop.modules.sys.service;

import cn.hoshiikunn.ezshop.modules.sys.vo.SysProfileVO;

/**
 * 个人资料业务接口
 */
public interface SysProfileService {

    /**
     * 查询个人资料
     */
    SysProfileVO getProfile();
}
