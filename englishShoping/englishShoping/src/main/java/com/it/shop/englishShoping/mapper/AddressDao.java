package com.it.shop.englishShoping.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.it.shop.englishShoping.domain.Address;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AddressDao extends BaseMapper<Address> {
    List<Address> getList(@Param("uid") String uid);

    Address getById(@Param("id") String id,@Param("uid") String uid);


}
