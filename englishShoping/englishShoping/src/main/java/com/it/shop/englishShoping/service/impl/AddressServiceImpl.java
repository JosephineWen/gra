package com.it.shop.englishShoping.service.impl;


import com.it.shop.englishShoping.domain.Address;
import com.it.shop.englishShoping.mapper.AddressDao;
import com.it.shop.englishShoping.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressDao addressDao;

//    @Cacheable(value = "user",key = "#userid",unless = "#result==null")
    @Override
    public synchronized List<Address> getList(String userid) {
        List<Address> list = addressDao.getList(userid);
        if (list.size()==0){
            list = new ArrayList<>();
        }
        return list;
    }



    @Override
    public synchronized Address getByUserId(String id,String uid) {
        Address byId = addressDao.getById(id, uid);
        if (byId==null){
            byId = new Address();
        }
        return byId;
    }


    @Override
    public synchronized boolean updateById(Address address) {
        return addressDao.updateById(address)>0;
    }


    @Override
    public synchronized String addByAddress(Address address) {
        Address address1 = address;
        String ids = UUID.randomUUID().toString();
        address1.setId(ids);
        if (addressDao.insert(address1)>0){
            return ids;
        }
        return null;
    }


    @Override
    public synchronized boolean deleteById(String userid) {
        return addressDao.deleteById(userid)>0;
    }


}
