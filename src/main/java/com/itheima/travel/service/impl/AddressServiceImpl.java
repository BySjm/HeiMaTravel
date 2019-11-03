package com.itheima.travel.service.impl;

import com.itheima.travel.dao.AddressDao;
import com.itheima.travel.domain.Address;
import com.itheima.travel.domain.User;
import com.itheima.travel.service.AddressService;
import com.itheima.travel.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class AddressServiceImpl implements AddressService {
    @Override
    public List<Address> findByUid(Integer uid) {
        SqlSession sqlSession = MyBatisUtils.openSession();
        AddressDao dao = sqlSession.getMapper(AddressDao.class);
        List<Address> list =  dao.findByUid(uid);
        MyBatisUtils.close(sqlSession);
        return list;
    }

    @Override
    public void save(Address address) {
        SqlSession sqlSession = MyBatisUtils.openSession();
        AddressDao dao = sqlSession.getMapper(AddressDao.class);
        dao.save(address);
        MyBatisUtils.close(sqlSession);
    }

    @Override
    public Address findByAid(Integer aid) {
        SqlSession sqlSession = MyBatisUtils.openSession();
        AddressDao dao = sqlSession.getMapper(AddressDao.class);
        return dao.findByAid(aid);
    }
}
