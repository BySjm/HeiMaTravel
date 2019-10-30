package com.itheima.travel.dao;

import com.itheima.travel.domain.Address;
import com.itheima.travel.domain.User;

import java.util.List;

public interface AddressDao {
    List<Address> findByUid(User user);

    void save(Address address);

}
