package com.wjb.reggie.service;

import com.wjb.reggie.domain.Address;

import java.util.List;

public interface AddressService {

    // 查询地址列表
    List<Address> addressList();

    // 新增地址
    void save(Address address);

    // 设置默认地址
    void setDefault(Long id);

    // 查询用户的默认收货地址
    Address findDefault();

    Address findById(Long id);

    void deleteBatchIds(List<Long> ids);
}
