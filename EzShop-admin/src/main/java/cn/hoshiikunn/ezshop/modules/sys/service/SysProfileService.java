package cn.hoshiikunn.ezshop.modules.sys.service;

import cn.hoshiikunn.ezshop.modules.sys.dto.SysProfilePasswordDTO;
import cn.hoshiikunn.ezshop.modules.sys.vo.SysProfileVO;

import javax.validation.Valid;
import java.util.List;

/**
 * 个人资料业务接口
 */
public interface SysProfileService {

    /**
     * 查询个人资料
     */
    SysProfileVO getProfile();

    /**
     * 获取当前登录用户所绑定的角色
     *
     * @return 角色名称集合
     */
    List<String> getRoles();

    /**
     * 修改当前登录用户密码
     *
     * @param dto 修改密码参数
     */
    void changePassword(SysProfilePasswordDTO dto);
}
