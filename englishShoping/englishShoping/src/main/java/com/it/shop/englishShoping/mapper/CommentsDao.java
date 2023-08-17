package com.it.shop.englishShoping.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.it.shop.englishShoping.domain.Comments;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentsDao extends BaseMapper<Comments> {
    @Select("SELECT s1.*,s3.username as userName  FROM comments s1 ,product s2,user s3 where s1.user_id = s3.id and s1.product_id = s2.id and s2.pinglinid = #{shopId} order by s1.status asc ")
    List<Comments> findByListss(@Param("shopId") String shopId);

    @Select("select  s1.*,s2.username as userName from  comments s1,user s2 where s1.user_id = s2.id and product_id =#{productId} and parent is null")
    List<Comments> seletByPrid(String productId);
}
