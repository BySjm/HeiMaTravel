package com.itheima.travel.web.servlet;

import com.itheima.travel.domain.ResultInfo;
import com.itheima.travel.domain.User;
import com.itheima.travel.service.UserService;
import org.apache.commons.beanutils.BeanUtils;

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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
