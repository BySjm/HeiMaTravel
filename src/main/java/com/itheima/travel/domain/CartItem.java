package com.itheima.travel.domain;

import lombok.Data;

@Data
public class CartItem {

    private Route route;//商品（线路）
    private Integer num;//购买数量
    private Double subTotal;//小计

    public Double getSubTotal() {
        return route.getPrice() * num;
    }
}
