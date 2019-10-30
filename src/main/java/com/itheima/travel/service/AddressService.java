package com.itheima.travel.service;

import com.itheima.travel.domain.Address;
import com.itheima.travel.domain.User;

import java.util.List;

public interface AddressService {

    List<Address> findByUid(User user);

    void save(Address address);
}
