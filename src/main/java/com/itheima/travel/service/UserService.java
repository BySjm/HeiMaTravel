package com.itheima.travel.service;

import com.itheima.travel.domain.ResultInfo;
import com.itheima.travel.domain.User;

public interface UserService {
    // 注册
    ResultInfo register(User param);

    // 根据用户名查询
    ResultInfo findByUsername(String username);

    // 发送短信
    ResultInfo sendSms(String telephone, String codeSms);

    // 密码登录
    ResultInfo pwdLogin(User param);

    // 根据手机号查询
    User findByTelephone(String telephone);

    // 根据uid查询
    User findByUid(Integer uid);

    // 更新用户信息
    void updateInfo(User param);
}
