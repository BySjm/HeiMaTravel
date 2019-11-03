package com.itheima.travel.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.travel.domain.ResultInfo;
import com.itheima.travel.factory.BeanFactory;
import com.itheima.travel.service.OrderService;
import com.itheima.travel.util.PayUtils;
import com.itheima.travel.web.servlet.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/pay")
public class PayServlet extends BaseServlet {

    //订单
    OrderService orderService = (OrderService) BeanFactory.getBean("OrderService");

    //转发支付链接
    protected void getPayUrl(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String oid = request.getParameter("oid");
        String payUrl = PayUtils.createOrder(oid, 1);
        request.setAttribute("oid",oid);
        request.setAttribute("payUrl",payUrl);
        request.getRequestDispatcher("/pay.jsp").forward(request,response);
    }

    //模板
    protected void isPay(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String oid = request.getParameter("oid");
        ResultInfo resultInfo = orderService.isPay(oid);
        writeJsonToClient(resultInfo,response);
    }

    //模板
    protected void template(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}
