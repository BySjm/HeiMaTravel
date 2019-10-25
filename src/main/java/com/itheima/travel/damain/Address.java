package com.itheima.travel.damain;

import lombok.Data;

import java.io.Serializable;

/**
 * 地址实体类
 */
@Data
public class Address implements Serializable {

    private Integer aid;

    private String contact;

    private String address;

    private String telephone;

    private String isDefault;

    private User user;



}
