package com.itheima.travel.dao;

import com.itheima.travel.domain.Route;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RouteDao {
    List<Route> findByPage(@Param("index") Integer index, @Param("pageSize") Integer pageSize, @Param("cid") String cid, @Param("rname") String rname);

    Integer findCount(@Param("cid") String cid, @Param("rname") String rname);

    Route findDetail(String rid);
}
