package com.it.shop.englishShoping.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.it.shop.englishShoping.domain.Product;
import com.it.shop.englishShoping.domain.Shop;
import com.it.shop.englishShoping.mapper.ShopDao;
import com.it.shop.englishShoping.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
    private ShopDao shopDao;


    @Override
    public List<Product> getByShopList(String pinglinid) {
        List<Product> products =    shopDao.getByShopList(pinglinid);
        if (products.size()==0){
            products = new ArrayList<>();
        }
        return products;
    }

    @Override
    public Shop getByUser(String userid) {
        System.out.println(userid);
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid",userid);
        Shop shop = shopDao.selectOne(queryWrapper);
        return shop;
    }

    @Override
    public void updateByIdToImgurl(String id, String originalFilename) {
        shopDao.updateByIdToImgurl(id,originalFilename);
    }

    @Override
    public Shop getByShopId(String id) {
        Shop shop = shopDao.selectById(id);
        if (shop==null){
            shop = new Shop();
        }
        return shop;
    }

    @Override
    public boolean update(Shop shop) {
        return shopDao.updateById(shop)>0;
    }

    @Override
    public boolean add(Shop shop) {
        Shop shop1 = shop;
        shop1.setId(UUID.randomUUID().toString());
        return shopDao.insert(shop1)>0;
    }
}
