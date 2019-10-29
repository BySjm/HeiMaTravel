package com.itheima.travel.dao;

import com.itheima.travel.domain.User;

public interface UserDao {

    void register(User user);

    User findByUsername(String username);

    User findByTelephone(String telephone);

    User findByUid(int uid);
}
