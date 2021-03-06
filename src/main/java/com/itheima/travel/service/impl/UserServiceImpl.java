package com.itheima.travel.service.impl;

import com.itheima.travel.domain.ResultInfo;
import com.itheima.travel.domain.User;
import com.itheima.travel.dao.UserDao;
import com.itheima.travel.service.UserService;
import com.itheima.travel.util.Md5Utils;
import com.itheima.travel.util.MyBatisUtils;
import com.itheima.travel.util.SmsUtils;
import org.apache.ibatis.session.SqlSession;

public class UserServiceImpl implements UserService {
    //注册
    public ResultInfo register(User user) {
        SqlSession sqlSession = MyBatisUtils.openSession();
        UserDao dao = sqlSession.getMapper(UserDao.class);
        User u1 = dao.findByUsername(user.getUsername());
        if (u1 != null) {
            MyBatisUtils.close(sqlSession);
            return new ResultInfo(false, "该用户名已存在");
        }
        User u2 = dao.findByTelephone(user.getTelephone());
        if (u2 != null) {
            MyBatisUtils.close(sqlSession);
            return new ResultInfo(false, "手机号已被注册");
        }
        String password = user.getPassword();
        password = Md5Utils.encodeByMd5(password + "acv");
        user.setPassword(password);
        dao.register(user);
        MyBatisUtils.close(sqlSession);
        return new ResultInfo(true);
    }

    //根据用户名查询
    public ResultInfo findByUsername(String username) {
        SqlSession sqlSession = MyBatisUtils.openSession();
        UserDao dao = sqlSession.getMapper(UserDao.class);
        User user = dao.findByUsername(username);
        MyBatisUtils.close(sqlSession);
        if (user != null) {
            return new ResultInfo(false, "该用户名已存在");
        } else {
            return new ResultInfo(true, "可以注册");
        }
    }

    //根据手机号查询
    public User findByTelephone(String telephone) {
        SqlSession sqlSession = MyBatisUtils.openSession();
        UserDao dao = sqlSession.getMapper(UserDao.class);
        User user = dao.findByTelephone(telephone);
        MyBatisUtils.close(sqlSession);
        return user;
    }

    //发送短信
    public ResultInfo sendSms(String telephone, String codeSms) {
        String signName = "BySjm";
        String templateCode = "SMS_176521278";
        String code = "{\"code\":\"" + codeSms + "\"}";
        try {
            // 4.调用工具类发送短信
            //SmsUtils.sendSms(telephone, signName, templateCode, code);
            System.out.println("短信验证码：" + codeSms);
            // 5.返回结果
            return new ResultInfo(true, "短信发送成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultInfo(false, "短信发送失败");
        }
    }

    //密码登录
    public ResultInfo pwdLogin(User param) {
        SqlSession sqlSession = MyBatisUtils.openSession();
        UserDao dao = sqlSession.getMapper(UserDao.class);
        User user = dao.findByUsername(param.getUsername());
        String password = param.getPassword();
        password = Md5Utils.encodeByMd5(password + "acv");
        if (user == null || !password.equals(user.getPassword())) {
            return new ResultInfo(false, "用户名或密码错误");
        }
        return new ResultInfo(true, "登录成功", user);
    }

    //根据Uid查询用户
    public User findByUid(Integer uid) {
        SqlSession sqlSession = MyBatisUtils.openSession();
        UserDao dao = sqlSession.getMapper(UserDao.class);
        return dao.findByUid(uid);
    }

    //更新用户信息
    public void updateInfo(User user) {
        SqlSession sqlSession = MyBatisUtils.openSession();
        UserDao dao = sqlSession.getMapper(UserDao.class);
        dao.updateInfo(user);
        MyBatisUtils.close(sqlSession);
    }
}
