package com.itheima.travel.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Cart {

    private Integer cartNum; //购物车商品数量
    private List<CartItem> cartItems = new ArrayList<>(); //购物项列表
    private Double cartTotal; //消费总金额

    public CartItem findCartItemByRid(Integer rid){
        for (CartItem cartItem : cartItems) {
            if (cartItem.getRoute().getRid() == rid) {
                return cartItem;
            }
        }
        return null;
    }

    public Integer getCartNum() {
        cartNum = 0;
        for (CartItem cartItem : cartItems) {
            cartNum += cartItem.getNum();
        }
        return cartNum;
    }

    public Double getCartTotal() {
        cartTotal = 0d;
        for (CartItem cartItem : cartItems) {
            cartTotal += cartItem.getSubTotal();
        }
        return cartTotal;
    }
}
