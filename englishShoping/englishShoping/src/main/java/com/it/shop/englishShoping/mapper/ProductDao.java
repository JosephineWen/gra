package com.it.shop.englishShoping.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.it.shop.englishShoping.domain.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ProductDao extends BaseMapper<Product> {
    List<Product> getList();
    List<Product> getListAll();

    Product getById(@Param("id") String id);

    List<Product> getByTypeList(@Param("typeid") String typeid);

    List<Product> getBySearch(@Param("text") String text);

    void updateByNumber(String id, Integer number);

    void updateByCode(@Param("id") String id,@Param("code") String code);

    List<Product> getByPriceList();

    List<Product> getByPriceList2();

    List<Product> getBytimeList();


    List<Product> getListAllTwo(@Param("id") String id);

    List<Product> findBySearch(@Param("search") String search);
}
