package com.itheima.travel.service;

import com.itheima.travel.domain.ResultInfo;
import com.itheima.travel.domain.User;
import com.itheima.travel.dao.UserDao;
import com.itheima.travel.util.Md5Utils;
import com.itheima.travel.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

public class UserService {

    public ResultInfo register(User user) {
        SqlSession sqlSession = MyBatisUtils.openSession();
        UserDao dao = sqlSession.getMapper(UserDao.class);
        User u1 = dao.findByUsername(user.getUsername());
        if (u1 != null){
            MyBatisUtils.close(sqlSession);
            return new ResultInfo(false,"该用户名已存在");
        }
        User u2 = dao.findByTelephone(user.getTelephone());
        if (u2 != null){
            MyBatisUtils.close(sqlSession);
            return new ResultInfo(false,"手机号已被注册");
        }
        String password = user.getPassword();
        password = Md5Utils.encodeByMd5(password + "acv");
        user.setPassword(password);
        dao.register(user);
        MyBatisUtils.close(sqlSession);
        return new ResultInfo(true);
    }
}
