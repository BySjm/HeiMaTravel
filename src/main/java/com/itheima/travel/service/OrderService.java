package com.itheima.travel.service;

import com.itheima.travel.domain.Order;
import com.itheima.travel.domain.ResultInfo;

import java.util.Map;

public interface OrderService {

    void save(Order order);

    void payNotify(Map param);

    ResultInfo isPay(String oid);
}
