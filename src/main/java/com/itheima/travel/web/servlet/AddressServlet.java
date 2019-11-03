package com.itheima.travel.web.servlet;

import com.itheima.travel.domain.Address;
import com.itheima.travel.domain.ResultInfo;
import com.itheima.travel.domain.User;
import com.itheima.travel.factory.BeanFactory;
import com.itheima.travel.service.AddressService;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

@WebServlet("/address")
public class AddressServlet extends BaseServlet {

    AddressService service = (AddressService) BeanFactory.getBean("AddressService");

    //模板
    protected void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath());
            return;
        }
        List<Address> list = service.findByUid(user.getUid());
        request.setAttribute("list", list);
        request.getRequestDispatcher("home_address.jsp").forward(request, response);
    }

    //保存地址
    protected void save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            User user = (User) request.getSession().getAttribute("user");
            if (user == null) {
                response.sendRedirect(request.getContextPath());
                return;
            }
            Map<String, String[]> parameterMap = request.getParameterMap();
            Address address = new Address();
            BeanUtils.populate(address, parameterMap);
            address.setUser(user);
            service.save(address);
            response.sendRedirect(request.getContextPath() + "/address?action=findAll");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("服务器忙..");
        }
    }

    //模板
    protected void template(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
