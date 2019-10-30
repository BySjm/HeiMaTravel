package com.itheima.travel.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.travel.domain.Category;
import com.itheima.travel.factory.BeanFactory;
import com.itheima.travel.service.CategoryService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.io.IOException;

@WebServlet("/category")
public class CategoryServlet extends BaseServlet{

    CategoryService service = (CategoryService)BeanFactory.getBean("CategoryService");

    //模板
    protected void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> list = service.findAll();
        BaseServlet.writeJsonToClient(list,response);
    }

    //模板
    protected void template(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}
