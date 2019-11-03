package com.itheima.travel.service.impl;

import com.itheima.travel.dao.AddressDao;
import com.itheima.travel.dao.OrderDao;
import com.itheima.travel.dao.OrderItemDao;
import com.itheima.travel.domain.Order;
import com.itheima.travel.domain.OrderItem;
import com.itheima.travel.domain.ResultInfo;
import com.itheima.travel.service.OrderService;
import com.itheima.travel.util.MyBatisUtils;
import com.itheima.travel.util.PayUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.Map;

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

    @Override
    public void payNotify(Map param) {
        //校验
        PayUtils.checkResultCode(param);
        //获取订单ID
        String oid = (String) param.get("out_trade_no");
        SqlSession sqlSession = MyBatisUtils.openSession();
        OrderDao orderDao = sqlSession.getMapper(OrderDao.class);
        orderDao.updateState(oid);
        MyBatisUtils.close(sqlSession);
    }

    @Override
    public ResultInfo isPay(String oid) {
        SqlSession sqlSession = MyBatisUtils.openSession();
        OrderDao orderDao = sqlSession.getMapper(OrderDao.class);
        Order order = orderDao.findByOid(oid);
        if (order.getState() == 1){
            return new ResultInfo(true,"支付成功");
        }else {
            return new ResultInfo(false,"支付失败");
        }
    }
}
