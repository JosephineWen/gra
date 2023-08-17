package com.it.shop.englishShoping.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.it.shop.englishShoping.domain.Tags;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TagsMapper extends BaseMapper<Tags> {


    @Select("select * from tags where  LOWER(text)  like LOWER(#{search}) ")
    List<Tags> selectListBySearch(@Param("search") String search);
}
