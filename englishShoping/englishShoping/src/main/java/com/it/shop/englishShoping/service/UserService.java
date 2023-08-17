package com.it.shop.englishShoping.service;
import com.it.shop.englishShoping.domain.User;
import com.it.shop.englishShoping.utils.Result;

import java.util.List;

public interface UserService {


    Result login(User user);

    List<User> getListUser(Integer code);

    boolean updateByMoney(String id, Double money);

    User getById(String id);

    void updateByIdToImgurl(String id,String imgurl);


    boolean updateById(User user);

    boolean add(User user);


    void deleteById(String id);

    Result insertPhone(String phone);

    Result verifyCode(String phone,String code);

    Result updateStatus(User user);

    boolean addTwo(User user);
}
