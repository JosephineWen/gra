package com.it.shop.englishShoping.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.it.shop.englishShoping.domain.Order;
import com.it.shop.englishShoping.domain.Product;
import com.it.shop.englishShoping.domain.Shop;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ShopDao extends BaseMapper<Shop> {

    void updateByStatus(@Param("id")String id,@Param("status") String status);


    List<Order> getListByStatus(@Param("uid")String uid, @Param("status") String status);


    List<Order> getListByTime();

    List<Product> getByShopList(@Param("pinglinid") String pinglinid);

    void updateByIdToImgurl(@Param("id") String id,@Param("imgurl") String imgurl);
}
