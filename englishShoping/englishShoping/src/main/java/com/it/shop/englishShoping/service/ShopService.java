package com.it.shop.englishShoping.service;


import com.it.shop.englishShoping.domain.Product;
import com.it.shop.englishShoping.domain.Shop;

import java.util.List;

public interface ShopService {

    List<Product> getByShopList(String pinglinid);

    Shop getByUser(String userid);

    void updateByIdToImgurl(String id, String originalFilename);

    Shop getByShopId(String id);

    boolean update(Shop shop);

    boolean add(Shop shop);
}
