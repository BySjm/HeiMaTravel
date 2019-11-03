package com.itheima.travel.service.impl;

import com.itheima.travel.dao.AddressDao;
import com.itheima.travel.dao.OrderDao;
import com.itheima.travel.dao.OrderItemDao;
import com.itheima.travel.domain.Order;
import com.itheima.travel.domain.OrderItem;
import com.itheima.travel.service.OrderService;
import com.itheima.travel.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

public class OrderServiceImpl implements OrderService {
    @Override
    public void save(Order order) {
        SqlSession sqlSession = MyBatisUtils.openSession();
        OrderDao orderDao = sqlSession.getMapper(OrderDao.class);
        OrderItemDao orderItemDao = sqlSession.getMapper(OrderItemDao.class);
        //保存订单
        orderDao.save(order);
        //保存订单项
        for (OrderItem orderItem : order.getOrderItemList()) {
            orderItemDao.save(orderItem);
        }
        MyBatisUtils.close(sqlSession);
    }
}
