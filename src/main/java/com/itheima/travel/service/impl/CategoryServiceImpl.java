package com.itheima.travel.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.travel.dao.CategoryDao;
import com.itheima.travel.domain.Category;
import com.itheima.travel.service.CategoryService;
import com.itheima.travel.util.JedisUtils;
import com.itheima.travel.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.List;

public class CategoryServiceImpl implements CategoryService {

    @Override
    public List findAll() {
        List<Category> categoryList = null;
        ObjectMapper objectMapper = new ObjectMapper();
        Jedis jedis = JedisUtils.getJedis();
        if (jedis.exists("travel_category")) {
            String json = jedis.get("travel_category");
            try {
                categoryList = objectMapper.readValue(json, List.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            SqlSession sqlSession = MyBatisUtils.openSession();
            CategoryDao dao = sqlSession.getMapper(CategoryDao.class);
            categoryList = dao.findAll();
            try {
                String json = objectMapper.writeValueAsString(categoryList);
                jedis.set("travel_category",json);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            MyBatisUtils.close(sqlSession);
        }
        jedis.close();
        return categoryList;
    }
}
