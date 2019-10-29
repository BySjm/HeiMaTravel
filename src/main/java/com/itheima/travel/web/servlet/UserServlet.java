package com.itheima.travel.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.travel.domain.ResultInfo;
import com.itheima.travel.domain.User;
import com.itheima.travel.service.UserService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.RandomStringUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/user")
public class UserServlet extends BaseServlet {

    UserService service = new UserService();

    protected void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String smsCode = request.getParameter("smsCode");
        String smsCodeServer = (String) request.getSession().getAttribute("smsCodeServer");
        if (smsCodeServer == null || !smsCodeServer.equals(smsCode)) {
            request.setAttribute("resultInfo", new ResultInfo(false, "验证码不正确"));
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }
        Map<String, String[]> parameterMap = request.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user, parameterMap);
            ResultInfo resultInfo = service.register(user);
            if (!resultInfo.getSuccess()) {
                request.setAttribute("resultInfo", resultInfo);
                request.getRequestDispatcher("/register.jsp").forward(request, response);
            } else {
                //清除session中存入的验证码信息
                request.getSession().removeAttribute("smsCodeServer");
                response.sendRedirect(request.getContextPath() + "/register_ok.jsp");
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
        ResultInfo resultInfo = null;
        String telephone = request.getParameter("telephone");
        User user = service.findByTelephone(telephone);
        ObjectMapper objectMapper = new ObjectMapper();
        if (user != null) {
            resultInfo = new ResultInfo(false, "手机号已被注册");
        } else {
            resultInfo = new ResultInfo(true, "可以注册");
        }
        String json = objectMapper.writeValueAsString(resultInfo);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }

    //发送短信验证码
    protected void sendSms(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String telephone = request.getParameter("telephone");
        String smsCodeServer = RandomStringUtils.randomNumeric(6);
        ResultInfo resultInfo = service.sendSms(telephone, smsCodeServer);
        if (resultInfo.getSuccess()) {// 成功
            request.getSession().setAttribute("smsCodeServer", smsCodeServer);
        }
        // 5.将resultInfo转为json
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(resultInfo);
        // 6.响应到客户端
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }

    //密码登录
    protected void pwdLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResultInfo resultInfo = null;
        try {
            Map<String, String[]> parameterMap = request.getParameterMap();
            User user = new User();
            BeanUtils.populate(user, parameterMap);
            resultInfo = service.pwdLogin(user);
            if (resultInfo.getSuccess()) {
                request.getSession().setAttribute("user", user);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo = new ResultInfo(false, "服务器异常，请稍后再试...");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(resultInfo);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }

    //手机登录
    protected void telLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResultInfo resultInfo = null;
        String telephone = request.getParameter("telephone");
        String smsCode = request.getParameter("smsCode");
        String smsCodeServer = (String) request.getSession().getAttribute("smsCodeServer");
        if (smsCodeServer == null || !smsCode.equals(smsCodeServer)) {
            resultInfo = new ResultInfo(false, "验证码不匹配");
        } else {
            User user = service.findByTelephone(telephone);
            if (user != null) {
                resultInfo = new ResultInfo(true, "登录成功");
                request.getSession().removeAttribute("smsCodeServer");
                request.getSession().setAttribute("user", user);
            } else {
                resultInfo = new ResultInfo(false, "验证码和手机号不匹配");
            }
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(resultInfo);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }

    //登出
    protected void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().removeAttribute("user");
        response.sendRedirect(request.getContextPath());
    }

    //个人中心用户信息数据回显
    protected void userInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User)request.getSession().getAttribute("user");
        user = service.findByUid(user.getUid());
        request.getSession().setAttribute("user",user);
        request.getRequestDispatcher("/home_index.jsp").forward(request,response);
    }

    //模板
    protected void template(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
