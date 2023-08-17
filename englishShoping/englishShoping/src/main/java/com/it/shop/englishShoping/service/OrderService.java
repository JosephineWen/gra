package com.it.shop.englishShoping.service;

import com.it.shop.englishShoping.domain.Order;

import java.util.List;

public interface OrderService {

    boolean updateByStatus(Order order);

    List<Order> getListByTime();

    List<Order> getListByStatus(String uid,String status);

    void  deleteById(String id);

    Order addByOrder(Order order);


    boolean updateByStatus2(Order order);

    List<Order> getListByStatusorPid(String pinglinid);

    String addByOrder2(Order order);

    public List<Order> getListByStatusorPid2(String pinglinid);

    List<Order> addOrderListById(Order order);

    List<Order> getListByIds(String id);

    Long selectFind(String userid);
}
