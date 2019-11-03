package com.itheima.travel.web.servlet;

import com.itheima.travel.domain.*;
import com.itheima.travel.factory.BeanFactory;
import com.itheima.travel.service.AddressService;
import com.itheima.travel.service.OrderService;
import com.itheima.travel.service.RouteService;
import com.itheima.travel.util.CartUtils;
import com.itheima.travel.util.JedisUtils;
import com.itheima.travel.util.UuidUtils;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet("/cart")
public class CartServlet extends BaseServlet {

    //购物车
    RouteService routeService = (RouteService) BeanFactory.getBean("RouteService");
    //地址
    AddressService addressService = (AddressService) BeanFactory.getBean("AddressService");
    //订单
    OrderService orderService = (OrderService) BeanFactory.getBean("OrderService");


    //订单结算
    protected void subOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //判断登录状态
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath());
            return;
        }
        String addressId = request.getParameter("addressId");
        Address address = addressService.findByAid(Integer.parseInt(addressId));
        Cart cart = CartUtils.getCartToRedis(user);
        //封装到订单实体
        Order order = new Order();
        order.setOid(UuidUtils.getUuid());
        order.setOrdertime(new Date());
        order.setTotal(cart.getCartTotal());
        order.setState(0);
        order.setAddress(address.getAddress());
        order.setContact(address.getContact());
        order.setTelephone(address.getTelephone());
        order.setUser(user);
        //封装订单项实体
        List<OrderItem> orderItemList = new ArrayList<>();
        List<CartItem> cartItems = cart.getCartItems();
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem;
            orderItem = new OrderItem();
            //orderItem.setItemid();
            orderItem.setItemtime(new Date());
            orderItem.setNum(cartItem.getNum());
            orderItem.setSubtotal(cartItem.getSubTotal());
            orderItem.setRoute(cartItem.getRoute());
            orderItem.setOrder(order);
            orderItemList.add(orderItem);
        }
        //System.out.println(orderItemList);
        //System.out.println(order);
        // 将订单项列表，封装到 order订单中
        order.setOrderItemList(orderItemList);
        //保存订单
        orderService.save(order);
        //清空购物车
        Jedis jedis = JedisUtils.getJedis();
        jedis.del("travel_cart_" + user.getUsername());
        jedis.close();
        //重定向
        response.sendRedirect(request.getContextPath()+"/pay.jsp");
    }

    //准备订单页面
    protected void prepareOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //判断登录状态
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath());
            return;
        }
        List<Address> addressList = addressService.findByUid(user.getUid());
        Cart cart = CartUtils.getCartToRedis(user);
        request.setAttribute("cart",cart);
        request.setAttribute("addressList",addressList);
        request.getRequestDispatcher("order_info.jsp").forward(request,response);
    }
    //删除订单项
    protected void delCartItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //判断登录状态
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath());
            return;
        }
        String rid = request.getParameter("rid");
        Cart cart = CartUtils.getCartToRedis(user);
        CartItem cartItemByRid = cart.findCartItemByRid(Integer.parseInt(rid));
        List<CartItem> cartItems = cart.getCartItems();
        for (CartItem cartItem : cartItems) {
            if (cartItemByRid.equals(cartItem)){
                cartItems.remove(cartItemByRid);
                break;
            }
        }
        CartUtils.setCartToRedis(user,cart);
        response.sendRedirect(request.getContextPath()+"/cart?action=findAll");
    }

    //查询购物车
    protected void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //判断登录状态
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath());
            return;
        }
        Cart cart = CartUtils.getCartToRedis(user);
        request.setAttribute("cart",cart);
        request.getRequestDispatcher("cart.jsp").forward(request,response);
    }

    //添加购物车
    protected void addCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //判断登录状态
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath());
            return;
        }
        String rid = request.getParameter("rid");
        Integer num = Integer.parseInt(request.getParameter("num"));
        //返回此购物项
        Route route = routeService.findDetail(rid);
        //从Redis中获取购物车对象
        Cart cart = CartUtils.getCartToRedis(user);
        //判断购物车是否有此购物项,然后封装
        CartItem cartItem = cart.findCartItemByRid(Integer.parseInt(rid));
        if (cartItem == null) {
            cartItem = new CartItem();
            cartItem.setRoute(route);
            cartItem.setNum(num);
            cart.getCartItems().add(cartItem);
        } else {
            cartItem.setNum(cartItem.getNum() + num);
        }
        //重新存到Redis
        CartUtils.setCartToRedis(user, cart);
        //把cartItem存入到request域
        request.setAttribute("cartItem", cartItem);
        //转发
        request.getRequestDispatcher("cart_success.jsp").forward(request, response);
    }

    //模板
    protected void template(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}
