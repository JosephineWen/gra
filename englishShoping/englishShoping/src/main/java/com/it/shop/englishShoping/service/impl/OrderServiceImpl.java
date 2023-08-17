package com.it.shop.englishShoping.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.it.shop.englishShoping.domain.Order;
import com.it.shop.englishShoping.domain.Product;
import com.it.shop.englishShoping.domain.ShoppingCar;
import com.it.shop.englishShoping.domain.User;
import com.it.shop.englishShoping.mapper.OrderDao;
import com.it.shop.englishShoping.mapper.ProductDao;
import com.it.shop.englishShoping.mapper.ShoppingCarDao;
import com.it.shop.englishShoping.mapper.UserDao;
import com.it.shop.englishShoping.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;


    @Autowired
    private ProductDao productDao;

    @Autowired
    private ShoppingCarDao carDao;

    @Autowired
    private UserDao userDao;

    Date date = new Date();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String time = simpleDateFormat.format(date);

    SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
    String time2 = simpleDateFormat2.format(date);

    @Transactional
    @Override
    public boolean updateByStatus(Order order) {
//        if (order.getMoney()!=null){
//            User byId = userDao.getById(order.getUserid());
//            if (byId.getMoney()<order.getMoney()){
//                return false;
//            }
//            byId.setMoney(byId.getMoney()-order.getMoney());
//            userDao.updateById(byId);
//        }

        orderDao.updateByStatus(order.getId(),order.getStatus(),order.getExpree());
        return true;
    }

    @Override
    public List<Order> getListByTime() {
        return orderDao.getListByStatusorPid(null);
    }

    @Override
    public List<Order> getListByStatus(String uid, String status) {
        List<Order> listByStatus = orderDao.getListByStatus(uid, status);
        if (listByStatus.size()==0){
            listByStatus = new ArrayList<>();
        }
        return listByStatus;
    }

    @Override
    public void deleteById(String id) {
        orderDao.deleteById(id);
    }

    @Transactional
    @Override
    public synchronized String addByOrder2(Order order){
        int mainid =0;
        try {
        Date now2 = new Date();
        Order order1 =order;
        long op = Long.parseLong(order1.getTime1());
        now2.setTime(op);
        order1.setTime1( simpleDateFormat.format(now2));
        System.out.println(order1.getTime1());
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code","3");
        queryWrapper.eq("pinglinid",order1.getShopid());
        Product product = productDao.selectOne(queryWrapper);
        order1.setProductid(product.getId());
        String quertime = order1.getTime2() + "%";
        List<String> byPidFindByTime = orderDao.getByPidFindByTime(product.getId(),quertime);
        if (byPidFindByTime.size()==0){
            order1.setKid("2");
            order1.setTime(order1.getTime1());
            order1.setNumber(1);
            order1.setStatus("1");
            orderDao.insert(order1);
            return "预约成功";
        }
        for (String s : byPidFindByTime) {
            LocalDateTime   localDateTime = LocalDateTime.parse(s, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                //用户预约
            LocalDateTime localDateTime2 = LocalDateTime.parse(order1.getTime1(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                long millis = localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
                long millis2 = localDateTime2.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
                order1.setKid("2");
                order1.setTime(order1.getTime1());
                order1.setNumber(1);
                order1.setStatus("1");
            if ((millis2+(1000*60*30))<millis || millis2>(millis+(1000*60*30))){
                mainid++;
            }
        }
        if (mainid==byPidFindByTime.size()){
            orderDao.insert(order1);
            return "预约成功";
        }
        return "该时间段已经有预约";
        }catch (Exception e){
            return "预约失败";
        }
    }

    @Transactional
    @Override
    public  synchronized Order addByOrder(Order order) {
        if ("1".equals(order.getStatus())){
        ShoppingCar byProductId = carDao.getByProductId(order.getProductid(), order.getUserid());
        if (byProductId!=null){
            QueryWrapper queryWrapper =new QueryWrapper<>();
            queryWrapper.eq("id",order.getProductid());
        Product byId = productDao.selectOne(queryWrapper);


        byId.setNumber(byId.getNumber()- order.getNumber());
        productDao.updateById(byId);
        //1.创建订单
        String uuid = UUID.randomUUID().toString();
        Order order1 = order;
        order1.setId(uuid);
        order1.setUserid(order.getUserid());
        order1.setKid("1");
        order1.setTime(time);
        order1.setAddressid(order.getAddressid());
        order1.setNumber(byProductId.getCount()==null?1:byProductId.getCount());
        order1.setMoney(byProductId.getCount()* byId.getPrice());
        order1.setStatus(order.getStatus());
        orderDao.insert(order1);
        //从购物车里面删除
        carDao.deleteById(byProductId.getId());
        return orderDao.selectById(uuid);
        }
        }

//        else {
//            ShoppingCar byProductId = carDao.getByProductId(order.getProductid(), order.getUserid());
//            if (byProductId!=null){
//                User byId1 = userDao.getById(order.getUserid());
//                byId1.setMoney(byId1.getMoney()- order.getMoney());
//                userDao.updateById(byId1);
//                Product byId = productDao.getById(order.getProductid());
//                //1.创建订单
//                String uuid = UUID.randomUUID().toString();
//                Order order1 = order;
//                order1.setId(uuid);
//                order1.setUserid(order.getUserid());
//                order1.setKid("1");
//                order1.setTime(time);
//                order1.setNumber(byProductId.getCount()==null?1:byProductId.getCount());
//                order1.setMoney(byProductId.getCount()* byId.getPrice());
//                order1.setStatus(order.getStatus());
//                orderDao.insert(order1);
//                //从购物车里面删除
//                carDao.deleteById(byProductId.getId());
//                return orderDao.selectById(uuid);
//            }
//        }
        return new Order();
    }

    @Override
    public boolean updateByStatus2(Order order) {
        UpdateWrapper<Order> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",order.getId());
        updateWrapper.set("status",order.getStatus());
        return orderDao.update(new Order(),updateWrapper)>0;
    }

    @Override
    public List<Order> getListByStatusorPid(String pinglinid) {
        List<Order>   orders = orderDao.getListByStatusorPid(pinglinid);

        if (orders.size()==0){
            orders = new ArrayList<>();
        }
        return orders;
    }


    @Override
    public List<Order> getListByStatusorPid2(String pinglinid) {
        List<Order>   orders = orderDao.getListByStatusorPid2(pinglinid);

        if (orders.size()==0){
            orders = new ArrayList<>();
        }
        return orders;
    }

    @Transactional
    @Override
    public List<Order> addOrderListById(Order order) {
        String userid = order.getUserid();
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid",userid);
        List<String> listarr = new ArrayList<>();
        List<ShoppingCar> list = carDao.selectList(queryWrapper);
        for (ShoppingCar car : list){
            Order newOrder = new Order();
            newOrder.setUserid(userid);
            newOrder.setStatus("1");
            newOrder.setAddressid(order.getAddressid());
            newOrder.setProductid(car.getProductid());
            newOrder.setNumber(car.getCount());
            System.out.println(newOrder+"---------");
            listarr.add(addByOrder(newOrder).getId());
        }
       List<Order> list1 = new ArrayList<>();
        for (String str : listarr){
            List<Order> listByIds = getListByIds(str);
            for (Order order1:listByIds){
                list1.add(order1);
            }
        }
        return list1;
    }

    @Override
    public List<Order> getListByIds(String id) {
        List<Order> listByStatus = orderDao.getListByStatusById(id);
        if (listByStatus.size()==0){
            listByStatus = new ArrayList<>();
        }
        return listByStatus;
    }

    @Override
    public Long selectFind(String userid) {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid",userid);

        Long aLong = carDao.selectCount(queryWrapper);
        return aLong;
    }
}
