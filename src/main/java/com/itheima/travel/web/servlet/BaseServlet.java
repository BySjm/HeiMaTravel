package com.itheima.travel.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.travel.domain.Category;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

public class BaseServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 接收请求参数 action
        String action = request.getParameter("action");
        try {
            // 改为反射的实现来实现方法的调用
            // 1.获取当前类的字节码对象
            Class clazz = this.getClass();
            // 2.根据action参数名，找到对应的Method对象
            Method method = clazz.getDeclaredMethod(action, HttpServletRequest.class, HttpServletResponse.class);// 可以获取全部修饰符声明的任意方法
            // 3.通过反射来执行
            method.invoke(this, request, response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("请求参数错误...");
        }
    }
    protected static void writeJsonToClient(Object object, HttpServletResponse response) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(object);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }
}
