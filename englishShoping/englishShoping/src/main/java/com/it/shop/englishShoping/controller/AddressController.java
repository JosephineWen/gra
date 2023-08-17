package com.it.shop.englishShoping.controller;

import com.it.shop.englishShoping.domain.Address;
import com.it.shop.englishShoping.service.AddressService;
import com.it.shop.englishShoping.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/address")
public class AddressController {
    @Autowired
    private AddressService addressService;



    @PostMapping("/getList")
   public List<Address> getList(@RequestBody Address address){
        log.info("获取收货地址:{}"+address);
        return addressService.getList(address.getUserid());
    }

    @PostMapping("/getByUserId")
    public  Address getById(@RequestBody Address address){
        log.info("获取收货地址:{}"+address);
        return addressService.getByUserId(address.getId(),address.getUserid());
    }


    @PostMapping("/updateById")
    public boolean updateById(@RequestBody Address address){
        log.info("更新收货地址:{}"+address);
        return addressService.updateById(address);
    }

    @PostMapping("/addByAddress")
    public Result addByAddress(@RequestBody Address address){
        log.info("添加收货地址:{}"+address);
        return Result.success(addressService.addByAddress(address));
    }

    @PostMapping("/deleteById")
    public boolean deleteById(@RequestBody Address address){
        log.info("删除收获地址:{}"+address);
        return addressService.deleteById(address.getId());
    }
}
