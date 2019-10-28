package com.itheima.travel.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.travel.domain.ResultInfo;
import com.itheima.travel.domain.User;
import com.itheima.travel.service.UserService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.RandomStringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/user")
public class UserServlet extends BaseServlet {

    UserService service = new UserService();

    protected void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> parameterMap = request.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user,parameterMap);
            ResultInfo resultInfo = service.register(user);
            if (!resultInfo.getSuccess()){
                request.setAttribute("resultInfo",resultInfo);
                request.getRequestDispatcher("/register.jsp").forward(request,response);
            }else {
                response.sendRedirect(request.getContextPath()+"/register_ok.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //判断用户名是否存在
    protected void findByUsername(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        ResultInfo resultInfo = service.findByUsername(username);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(resultInfo);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }

    //判断手机号
    protected void findByTelephone(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String telephone = request.getParameter("telephone");
        ResultInfo resultInfo = service.findByTelephone(telephone);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(resultInfo);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }

    protected void sendSms(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String telephone = request.getParameter("telephone");
        String smsCode = RandomStringUtils.randomNumeric(6);
        ResultInfo resultInfo = service.sendSms(telephone,smsCode);
        if (resultInfo.getSuccess()) {// 成功
            request.getSession().setAttribute("smsCode", smsCode);
        }
        // 5.将resultInfo转为json
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(resultInfo);
        // 6.响应到客户端
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
