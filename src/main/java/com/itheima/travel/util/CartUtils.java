package com.itheima.travel.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.travel.domain.Cart;
import com.itheima.travel.domain.User;
import redis.clients.jedis.Jedis;

import java.io.IOException;

public class CartUtils {

    public static void setCartToRedis(User user, Cart cart) throws JsonProcessingException {
        Jedis jedis = JedisUtils.getJedis();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(cart);
        jedis.set("travel_cart_" + user.getUsername(), json);
        jedis.close();
    }

    public static Cart getCartToRedis(User user) throws IOException {
        Jedis jedis = JedisUtils.getJedis();
        String json = jedis.get("travel_cart_" + user.getUsername());
        Cart cart = null;
        if (json != null){
            ObjectMapper objectMapper = new ObjectMapper();
            cart = objectMapper.readValue(json, Cart.class);
        }else {
            cart = new Cart();
        }
        jedis.close();
        return cart;
    }

}
