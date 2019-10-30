package com.itheima.travel.service.impl;

import com.itheima.travel.dao.CategoryDao;
import com.itheima.travel.domain.Category;
import com.itheima.travel.service.CategoryService;
import com.itheima.travel.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {

    @Override
    public List findAll() {
        SqlSession sqlSession = MyBatisUtils.openSession();
        CategoryDao dao = sqlSession.getMapper(CategoryDao.class);
        List<Category> list = dao.findAll();
        return list;
    }
}
