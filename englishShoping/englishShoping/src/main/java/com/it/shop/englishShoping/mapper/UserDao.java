package com.it.shop.englishShoping.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.it.shop.englishShoping.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserDao extends BaseMapper<User> {

    List<User> getListUser(@Param("code") Integer code);

    User loginUser(@Param("username")String username,@Param("password")String password);

    boolean updateByMoney(@Param("id")String id,@Param("money") Double money);


    User getById(@Param("id") String id);

    void updateByNumber(@Param("id") String id, @Param("number") Integer number);

     void updateByIdToImgurl(@Param("id") String id, @Param("imgurl") String imgurl);

    String getPhoneByPassword(@Param("phone") String phone);
}
