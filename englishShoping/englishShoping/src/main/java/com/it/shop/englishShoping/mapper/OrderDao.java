package com.it.shop.englishShoping.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.it.shop.englishShoping.domain.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface OrderDao extends BaseMapper<Order> {

    void updateByStatus(@Param("id")String id,@Param("status") String status,@Param("expree") String expree);


    List<Order> getListByStatus(@Param("uid")String uid,@Param("status") String status);


    List<Order> getListByStatusById(@Param("id")String id);

    List<Order> getListByTime();


    List<Order> getListByStatusorPid(@Param("pinglinid") String pinglinid);

    List<Order> getListByStatusorPid2(@Param("pinglinid") String pinglinid);

    List<String> getByPidFindByTime(@Param("pid")String pid,@Param("time")String time);
}
