package com.it.shop.englishShoping.controller;
import com.it.shop.englishShoping.domain.CarDto;
import com.it.shop.englishShoping.domain.ShoppingCar;
import com.it.shop.englishShoping.service.ShoppingCarService;
import com.it.shop.englishShoping.utils.Base64Util;
import com.it.shop.englishShoping.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/car")
public class ShoppingCarController {
    @Autowired
    private ShoppingCarService carService;

    @Autowired
    private Base64Util base64Util;

    @PostMapping("/list")
     public Result getByCarList(@RequestBody ShoppingCar car){
        return Result.success(carService.getByCarList(car.getUserid()));

     }

     @PostMapping("/updateByCount")
     public void updateByCount(@RequestBody ShoppingCar car){
        if (car.getId()!=null&&car.getCount()!=null) {
            carService.updateByCount(car.getId(), car.getCount());
        }
     }

     @PostMapping("/add")
    public void  addByCar(@RequestBody ShoppingCar car){
         carService.addByCar(car);
     }

    @PostMapping("/deleteById")
    public  void deleteByCarId(@RequestBody ShoppingCar car){
         carService.deleteByCarId(car.getId());
    }


    @PostMapping("/findByProductId")
    public ShoppingCar findByProductId(@RequestBody ShoppingCar car){
     return    carService.getByProductId(car.getProductid(),car.getUserid());
    }
}
