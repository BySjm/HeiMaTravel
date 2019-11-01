package com.itheima.travel.service.impl;

import com.itheima.travel.dao.RouteDao;
import com.itheima.travel.domain.PageBean;
import com.itheima.travel.domain.Route;
import com.itheima.travel.service.RouteService;
import com.itheima.travel.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class RouteServiceImpl implements RouteService {
    @Override
    public PageBean<Route> findByPage(Integer pageNum, Integer pageSize, String cid, String rname) {
        SqlSession sqlSession = MyBatisUtils.openSession();
        RouteDao routeDao = sqlSession.getMapper(RouteDao.class);
        PageBean<Route> pageBean = new PageBean<>();
        //总记录数
        Integer totalCount = routeDao.findCount(cid,rname);
        pageBean.setTotalCount(totalCount);
        //总页数
        Integer totalPage = new Double(Math.ceil(totalCount*1.0 / pageSize)).intValue();
        pageBean.setTotalPage(totalPage);
        //结果集
        Integer index = (pageNum - 1) * pageSize;
        List<Route> list = routeDao.findByPage(index,pageSize,cid, rname);
        pageBean.setList(list);
        //当前页
        pageBean.setPageNum(pageNum);
        //每页个数
        pageBean.setPageSize(pageSize);
        return pageBean;
    }
}
