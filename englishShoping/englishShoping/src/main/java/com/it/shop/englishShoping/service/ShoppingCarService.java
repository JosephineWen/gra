package com.it.shop.englishShoping.service;
import com.it.shop.englishShoping.domain.CarDto;
import com.it.shop.englishShoping.domain.ShoppingCar;

public interface ShoppingCarService {
    CarDto getByCarList(String uid);

    void updateByCount(String id,Integer count);

    void addByCar(ShoppingCar shoppingCar);

    void deleteByCarId(String id);

    ShoppingCar getByProductId(String pid, String uid);
}
