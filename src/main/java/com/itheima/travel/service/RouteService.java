package com.itheima.travel.service;

import com.itheima.travel.domain.PageBean;
import com.itheima.travel.domain.Route;


public interface RouteService {
    PageBean<Route> findByPage(Integer pageNum, Integer pageSize, String cid, String rname);

    Route findDetail(String rid);
}
