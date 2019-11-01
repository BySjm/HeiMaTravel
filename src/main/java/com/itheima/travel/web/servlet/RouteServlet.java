package com.itheima.travel.web.servlet;

import com.itheima.travel.domain.PageBean;
import com.itheima.travel.domain.Route;
import com.itheima.travel.factory.BeanFactory;
import com.itheima.travel.service.RouteService;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/route")
public class RouteServlet extends BaseServlet{

    RouteService routeService = (RouteService)BeanFactory.getBean("RouteService");

    //模板
    protected void findByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pageNumStr = request.getParameter("pageNum");
        String pageSizeStr = request.getParameter("pageSize");
        String rname = request.getParameter("rname");
        String cid = request.getParameter("cid");
        if (StringUtils.isEmpty(pageNumStr)){
            pageNumStr = "1";
        }
        if (StringUtils.isEmpty(pageSizeStr)){
            pageSizeStr = "10";
        }
        Integer pageNum = Integer.parseInt(pageNumStr);
        Integer pageSize = Integer.parseInt(pageSizeStr);
        PageBean<Route> pageBean = routeService.findByPage(pageNum,pageSize,cid,rname);
        request.setAttribute("pageBean",pageBean);
        request.setAttribute("cid",cid);
        request.setAttribute("rname",rname);
        request.getRequestDispatcher("/route_list.jsp").forward(request,response);
    }

    //模板
    protected void template(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}
