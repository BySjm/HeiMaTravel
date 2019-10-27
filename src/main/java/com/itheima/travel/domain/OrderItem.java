package com.itheima.travel.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单项实体
 */
@Data
public class OrderItem implements Serializable {

    private Integer itemid;  // 订单项id

    private Date itemtime; // 生成时间

    private Integer num;  // 购买数量

    private Double subtotal; // 小计

    private Route route;  // 商品（线路）

    private Order order;  // 订单
}
