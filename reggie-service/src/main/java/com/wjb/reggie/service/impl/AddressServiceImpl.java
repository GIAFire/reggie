package com.wjb.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wjb.reggie.common.UserHolder;
import com.wjb.reggie.domain.Address;
import com.wjb.reggie.mapper.AddressMapper;
import com.wjb.reggie.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressMapper addressMapper;

    // 查询地址列表
    @Override
    public List<Address> addressList() {
        // 1.构建条件
        LambdaQueryWrapper<Address> wrapper = new LambdaQueryWrapper<>();
        // SELECT * FROM `address_book` WHERE user_id = 1458310743471493121
        wrapper.eq(Address::getUserId, UserHolder.get().getId());
        // 2.查询并返回
        return addressMapper.selectList(wrapper);
    }

    // 新增地址
    @Override
    public void save(Address address) {
        // 1.补全信息
        address.setUserId(UserHolder.get().getId());

        // 2.mapper保存
        addressMapper.insert(address);
    }

    // 设置默认地址
    @Override
    public void setDefault(Long id) {
        // 1.把当前用户所有的地址设置成非默认
        // 1-1 准备实体
        Address address1 = new Address();
        address1.setIsDefault(0);
        // 1-2 构建条件
        LambdaQueryWrapper<Address> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Address::getUserId, UserHolder.get().getId());
        // 1-3 条件更新
        addressMapper.update(address1, wrapper);

        // ---------------------------------
        // 2.把当前地址设置为默认的
        // 2-1 准备实体
        Address address2 = new Address();
        address2.setId(id);
        address2.setIsDefault(1);
        // 2-2 主键更新
        addressMapper.updateById(address2);

    }

    // 查询用户的默认收货地址
    @Override
    public Address findDefault() {
        // 1.构建条件
        LambdaQueryWrapper<Address> wrapper = new LambdaQueryWrapper<>();
        // SELECT * FROM `address_book` WHERE user_id = 1458310743471493121 AND is_default = 1
        wrapper.eq(Address::getUserId, UserHolder.get().getId());
        wrapper.eq(Address::getIsDefault,1 );
        // 2.查询
        return addressMapper.selectOne(wrapper);
    }

    @Override
    public Address findById(Long id) {
        return addressMapper.selectById(id);
    }

    @Override
    public void deleteBatchIds(List<Long> ids) {
        addressMapper.deleteBatchIds(ids);
    }
}
