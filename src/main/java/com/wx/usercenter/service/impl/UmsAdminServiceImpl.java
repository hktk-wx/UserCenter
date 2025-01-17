package com.wx.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wx.usercenter.model.domain.UmsAdmin;
import com.wx.usercenter.model.vo.LoginUserVO;
import com.wx.usercenter.service.UmsAdminService;
import com.wx.usercenter.mapper.UmsAdminMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;

import static com.wx.usercenter.constant.UserConstant.USER_LOGIN_STATE;

/**
 * @author HP
 * @description 针对表【ums_admin(后台用户表)】的数据库操作Service实现
 * @createDate 2024-11-25 09:43:40
 */
@Service
public class UmsAdminServiceImpl extends ServiceImpl<UmsAdminMapper, UmsAdmin>
        implements UmsAdminService {

    public static final String SALT = "hktk";

    @Override
    public long register(String username, String password, String checkPassword) {
        // 校验是否为空
        if (StringUtils.isAllBlank(username, password, checkPassword)) {
            return -1;
        }
        if (username.length() < 4) {
            return -1;
        }
        if (password.length() < 4) {
            return -1;
        }

        if (!password.equals(checkPassword)) {
            return -1;
        }

        // 账户不能重复
        QueryWrapper<UmsAdmin> umsAdminQueryWrapper = new QueryWrapper<>();
        umsAdminQueryWrapper.eq("username", username);
        Long count = this.baseMapper.selectCount(umsAdminQueryWrapper);
        if (count > 0) {
            return -1;
        }
        // 进行加盐的加密
        String enPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());

        UmsAdmin umsAdmin = new UmsAdmin();
        umsAdmin.setUsername(username);
        umsAdmin.setPassword(enPassword);
        boolean saveResult = this.save(umsAdmin);
        if (!saveResult) {
            return -1;
        }

        return umsAdmin.getId();
    }

    @Override
    public LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // 校验
        if (StringUtils.isBlank(userAccount)) {
            return null;
        }
        if (userAccount.length() < 4) {
            return null;
        }
        if(userPassword.length() < 4) {
            return null;
        }
        String enPassword = DigestUtils.md5DigestAsHex((SALT + userAccount).getBytes());
        QueryWrapper<UmsAdmin> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", userAccount);
        userQueryWrapper.eq("password", enPassword);
        UmsAdmin umsAdmin = this.baseMapper.selectOne(userQueryWrapper);
        // 用户不存在
        if(umsAdmin == null) {
            return null;
        }
        request.getSession().setAttribute(USER_LOGIN_STATE,umsAdmin);
        return this.getLoginUserVO(umsAdmin);
    }

    @Override
    public LoginUserVO getLoginUserVO(UmsAdmin umsAdmin) {
        if (umsAdmin == null) {
            return null;
        }
        LoginUserVO loginUserVO = new LoginUserVO();
        BeanUtils.copyProperties(umsAdmin, loginUserVO);
        return loginUserVO;
    }
}




