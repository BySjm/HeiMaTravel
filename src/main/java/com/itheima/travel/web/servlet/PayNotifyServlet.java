package com.itheima.travel.web.servlet;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.itheima.travel.factory.BeanFactory;
import com.itheima.travel.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/payNotify")
public class PayNotifyServlet extends HttpServlet {

    //订单
    OrderService orderService = (OrderService) BeanFactory.getBean("OrderService");

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1.接收微信平台发送请求参数
        ServletInputStream inputStream = request.getInputStream();
        // 2.将请求流中的xml转为map集合
        XmlMapper xmlMapper = new XmlMapper();
        Map param = xmlMapper.readValue(inputStream, Map.class);
        // 3.调用orderSerivce，进行校验和修改订单状态
        orderService.payNotify(param);
        Map<String,String> result = new HashMap<>();
        result.put("return_code","SUCCESS");
        result.put("return_msg","OK");
        String xml = xmlMapper.writeValueAsString(result);
        response.getWriter().write(xml);
    }
}
