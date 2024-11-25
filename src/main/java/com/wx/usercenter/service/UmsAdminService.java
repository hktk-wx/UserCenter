package com.wx.usercenter.service;

import com.wx.usercenter.model.domain.UmsAdmin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wx.usercenter.model.vo.LoginUserVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author HP
* @description 针对表【ums_admin(后台用户表)】的数据库操作Service
* @createDate 2024-11-25 09:43:40
*/
public interface UmsAdminService extends IService<UmsAdmin> {

    long register(String username,String password,String checkPassword);

    LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request);

    LoginUserVO getLoginUserVO(UmsAdmin umsAdmin);
}
