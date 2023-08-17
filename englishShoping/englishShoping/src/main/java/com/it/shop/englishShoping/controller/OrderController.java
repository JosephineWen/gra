package com.it.shop.englishShoping.controller;
import com.it.shop.englishShoping.domain.Order;
import com.it.shop.englishShoping.domain.Product;
import com.it.shop.englishShoping.service.OrderService;
import com.it.shop.englishShoping.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;


    @PostMapping("/getCount")
    public Result getCount(@RequestBody Order order){
        return Result.success(orderService.selectFind(order.getUserid()));
    }

    @PostMapping("/addByOrder")
    public Result addByOrder(@RequestBody Order order){
        log.info("添加订单:{}"+order);
       return Result.success(orderService.addOrderListById(order));
    }


    @PostMapping("/addByOrder2")
    public  Map<String,String> addByOrder2(@RequestBody Order order){
        log.info("添加订单2:{}"+order);
        Map map = new HashMap<>();
        map.put("code", orderService.addByOrder2(order));
       return map;

    }

    @PostMapping("/updateByStatus2")
    public Map<String,String> updateByStatus2(@RequestBody Order order){
        log.info("更新订单状态2:{}"+order);
        boolean b = orderService.updateByStatus2(order);
        Map<String,String> map = new HashMap<>();
        if (b){
            map.put("code","200");
        }else{
            map.put("code","500");

        }
        return map;

    }


    @PostMapping("/updateByStatus")
    public Map<String,String> updateByStatus(@RequestBody Order order){
        log.info("更新状态订单:{}"+order);
        boolean b = orderService.updateByStatus(order);
        Map<String,String> map = new HashMap<>();
        if (b){
            map.put("code","200");
        }else{
            map.put("code","500");

        }
        return map;

    }

    @PostMapping("/getListByTime")
    public Result getListByTime(){
        log.info("获取全部订单:{}");
        return Result.success(orderService.getListByTime());
    }

    @PostMapping("/getListByStatus")
    public List<Order> getListByStatus(@RequestBody Order order){
        log.info("通过订单获取列表:{}"+order);
        return orderService.getListByStatus(order.getUserid(),order.getStatus());
    }
    @PostMapping("/getListByIds")
    public List<Order> getListByIds(@RequestBody Order order){
        log.info("通过订单获取列表:{}"+order);
        return orderService.getListByIds(order.getId());
    }


    @PostMapping("/getListByStatusorPid")
    public List<Order> getListByStatusorPid(@RequestBody Product product){
        log.info("通过状态获取全部订单:{}"+product);
        return orderService.getListByStatusorPid(product.getPinglinid());
    }

    @PostMapping("/getListByStatusorPid2")
    public List<Order> getListByStatusorPid2(@RequestBody Product product){
        log.info("通过店铺id获取订单列表:{}"+product);
        return orderService.getListByStatusorPid2(product.getPinglinid());
    }


    @PostMapping("/deleteById")
    public void  deleteById(@RequestBody Order order){
        log.info("删除订单:{}"+order);
        orderService.deleteById(order.getId());
    }






}
