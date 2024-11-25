package com.wx.usercenter.controller;

import com.wx.usercenter.model.dto.user.UserRegisterRequest;
import com.wx.usercenter.model.vo.LoginUserVO;
import com.wx.usercenter.service.UmsAdminService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName UserController
 * @Description 用户接口
 * @date 2024/11/25 12:31
 * @Create by 海阔天空
 * @Version 1.0
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UmsAdminService umsAdminService;
    /**
     * 用户注册
     *
     * @param userRegisterRequest
     * @return
     */
    @PostMapping("/register")
    public long userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            return -1;
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return -1;
        }
        long result = umsAdminService.register(userAccount, userPassword, checkPassword);
        return result;
    }

    /**
     * 用户登录
     *
     * @param userLogin 用户登录
     * @param request   请求
     * @return {@link LoginUserVO }
     */
    @PostMapping("/login")
    public LoginUserVO userLogin(@RequestBody UserRegisterRequest userLogin,HttpServletRequest request) {
        if(userLogin==null){
            return null;
        }
        String userAccount = userLogin.getUserAccount();
        String userPassword = userLogin.getUserPassword();
        if(StringUtils.isAnyBlank(userAccount, userPassword)){
            return null;
        }
        LoginUserVO loginUserVO = umsAdminService.userLogin(userAccount, userPassword, request);
        return loginUserVO;

    }

}
