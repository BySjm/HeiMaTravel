package com.itheima.travel.dao;

import com.itheima.travel.domain.Order;
import com.itheima.travel.domain.OrderItem;

public interface OrderItemDao {
    void save(OrderItem orderItem);
}
