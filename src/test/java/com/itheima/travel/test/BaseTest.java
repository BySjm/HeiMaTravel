package com.itheima.travel.test;

import com.itheima.travel.domain.Route;
import com.itheima.travel.factory.BeanFactory;
import com.itheima.travel.service.RouteService;
import org.junit.Test;

public class BaseTest {
    @Test
    public void test() throws Exception {
        RouteService routeService = (RouteService) BeanFactory.getBean("RouteService");
        Route route = routeService.findDetail("1");
        System.out.println(route);
    }
}
