package com.it.shop.englishShoping.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.it.shop.englishShoping.domain.Type;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface TypeDao extends BaseMapper<Type> {
    void addType(Type type);
}
