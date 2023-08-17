package com.it.shop.englishShoping.service.impl;
import com.it.shop.englishShoping.domain.CarDto;
import com.it.shop.englishShoping.domain.Product;
import com.it.shop.englishShoping.domain.ShoppingCar;
import com.it.shop.englishShoping.mapper.ProductDao;
import com.it.shop.englishShoping.mapper.ShoppingCarDao;
import com.it.shop.englishShoping.service.ShoppingCarService;
import com.it.shop.englishShoping.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ShoppingCarServiceImpl implements ShoppingCarService {

    @Autowired
    private ShoppingCarDao shoppingCarDao;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductDao productDao;



    @Transactional
    @Override
    public CarDto getByCarList(String uid) {
        CarDto carDto = new CarDto();
        List<ShoppingCar> list = shoppingCarDao.getList(uid);
        Double money = shoppingCarDao.countByMoney(uid);
        Integer count = shoppingCarDao.countByNumber(uid);
        if (list.size()==0){
            carDto.setCarList(new ArrayList<>());
        }
        if (money==null){
            carDto.setMoney(0.0);
        }
        if (count==0){
            carDto.setCount(0);
        }
        carDto.setCarList(list);
        carDto.setMoney(money);
        carDto.setCount(count);
        return carDto;
    }

    @Transactional
    @Override
    public  void  updateByCount(String id, Integer count) {

        ShoppingCar shoppingCar = shoppingCarDao.selectById(id);
        if (shoppingCar!=null) {
        Integer oldcount = shoppingCar.getCount();
        Product byId = productDao.getById(shoppingCar.getProductid());
        Integer number = byId.getNumber();
        if (count==0){
            //如果在购物车重数量为0那就删除该商品
          number = number + oldcount;
          byId.setNumber(number);
          productDao.updateById(byId);
          shoppingCarDao.deleteById(id);
        }else if (count > oldcount){
            //如果是增加就减去对应库存
            if (number-(count-oldcount)>0){
            number = number-(count-oldcount);
            byId.setNumber(number);
            productDao.updateById(byId);
            shoppingCarDao.updateCount(id,count);
            }else {
                System.out.println("Out of stock. Please restock.");
            }
        }else {
            //如果是减少就增加对应库存
            number = number + (oldcount-count);
            byId.setNumber(number);
            productDao.updateById(byId);
            shoppingCarDao.updateCount(id,count);
        }
        }

    }


    @Transactional
    @Override
    public void addByCar(ShoppingCar shoppingCar) {
        ShoppingCar shoppingCar2 = shoppingCar;
        ShoppingCar byProductId = shoppingCarDao.getByProductId(shoppingCar2.getProductid(), shoppingCar2.getUserid());
        if (byProductId==null){
        shoppingCar2.setId(UUID.randomUUID().toString());
        String productid = shoppingCar2.getProductid();
        Product byId = productDao.getById(productid);
        shoppingCar2.setCount(1);
        Integer number = byId.getNumber();
        if (number==0){
            //存量为0请及时补货
            System.out.println("存量为0请及时补货");
        }else {
            byId.setNumber(number-1);
            shoppingCarDao.insert(shoppingCar);
        }
        }else {
            Integer count = byProductId.getCount();
            count+=1;
            byProductId.setCount(count);
            shoppingCarDao.updateById(byProductId);
        }
    }

    @Override
    public void deleteByCarId(String id) {
         shoppingCarDao.deleteById(id);
    }

    @Override
    public ShoppingCar getByProductId(String pid, String uid) {
        ShoppingCar byProductId = shoppingCarDao.getByProductId(pid, uid);
        if (byProductId==null){
            byProductId = new ShoppingCar();
        }
        return byProductId;
    }


}
