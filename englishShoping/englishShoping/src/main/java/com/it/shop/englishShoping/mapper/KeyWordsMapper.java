package com.it.shop.englishShoping.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.it.shop.englishShoping.domain.Keywords;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface KeyWordsMapper extends BaseMapper<Keywords> {
  @Select("select id,text from key_words")
    List<Keywords> findByAll();
}
