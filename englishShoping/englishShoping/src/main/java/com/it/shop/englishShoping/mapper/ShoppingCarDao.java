package com.it.shop.englishShoping.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.it.shop.englishShoping.domain.ShoppingCar;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ShoppingCarDao extends BaseMapper<ShoppingCar> {

    List<ShoppingCar> getList(@Param("uid")String uid);

    void updateCount(@Param("id")String id,@Param("count") Integer count);

    boolean deleteById(@Param("id")String id);



    Double countByMoney(@Param("uid")String uid);

    Integer countByNumber(@Param("uid")String uid);


    ShoppingCar getByProductId(@Param("pid") String pid,@Param("uid") String uid);
}
