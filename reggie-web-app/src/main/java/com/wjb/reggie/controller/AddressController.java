package com.wjb.reggie.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.wjb.reggie.common.CustomException;
import com.wjb.reggie.common.ResultInfo;
import com.wjb.reggie.domain.Address;
import com.wjb.reggie.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class AddressController {

    @Autowired
    private AddressService addressService;

    // 查询地址列表
    @GetMapping("/address/list")
    public ResultInfo addressList() {
        // 调用service查询
        List<Address> addresseList = addressService.addressList();
        // 返回结果
        return ResultInfo.success(addresseList);
    }

    // 新增地址
    @PostMapping("/address")
    public ResultInfo save(@RequestBody Address address) { // 1.接收参数
        // 2.调用service保存
        addressService.save(address);

        // 3.返回成功
        return ResultInfo.success(null);

    }

    // 设置默认地址
    @PutMapping("/address/default")
    public ResultInfo setDefault(@RequestBody Map<String, Long> param) {// 1.接收参数
        Long id = param.get("id");
        // 2.调用service更新
        addressService.setDefault(id);
        // 3.返回成功
        return ResultInfo.success(null);
    }

    // 查询用户的默认收货地址
    @GetMapping("/address/default")
    public ResultInfo findDefault() {
        //  调用service查询
        Address address = addressService.findDefault();
        /*if (address == null) {
            throw new CustomException("请设置默认地址后，结算购物车");
        }*/
        return ResultInfo.success(address);
    }

    @GetMapping("/address/{id}")
    public ResultInfo findById(@PathVariable Long id) {
        Address address = addressService.findById(id);
        return ResultInfo.success(address);
    }

    // 删除地址
    @DeleteMapping("/address")
    public ResultInfo delete(@RequestParam List<Long> ids) { // 1.接收参数
        // 2.调用service保存
        if (CollectionUtil.isNotEmpty(ids)) {
            addressService.deleteBatchIds(ids);
        }
        // 3.返回成功
        return ResultInfo.success(null);

    }

}
