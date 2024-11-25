package com.wx.usercenter.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName UserLoginRequest
 * @Description TODO
 * @date 2024/11/25 16:41
 * @Create by 海阔天空
 * @Version 1.0
 */
@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = 42L;

    private String userAccount;

    private String userPassword;
}
