package com.itheima.travel.dao;

import com.itheima.travel.domain.Order;

public interface OrderDao {
    void save(Order order);

    void updateState(String oid);

    Order findByOid(String oid);
}
