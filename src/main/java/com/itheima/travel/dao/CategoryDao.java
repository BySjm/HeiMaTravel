package com.itheima.travel.dao;

import com.itheima.travel.domain.Category;

import java.util.List;

public interface CategoryDao {

    List<Category> findAll();
}
