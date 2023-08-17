package com.it.shop.englishShoping.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.it.shop.englishShoping.domain.Product;
import com.it.shop.englishShoping.domain.Reporting;
import com.it.shop.englishShoping.domain.User;
import com.it.shop.englishShoping.mapper.ProductDao;
import com.it.shop.englishShoping.mapper.ReportingDao;
import com.it.shop.englishShoping.mapper.UserDao;
import com.it.shop.englishShoping.service.ReportingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ReportingServiceImpl implements ReportingService {


    @Autowired
    private ReportingDao reportingDao;

    @Autowired
    UserDao userDao;

    @Autowired
    ProductDao productDao;

    @Override
    public List<Reporting> findByAll() {
        List<Reporting> list = reportingDao.selectList(new QueryWrapper());
        List<Reporting> list2 = new ArrayList<>();
        for (Reporting reporting : list){
            String userId = reporting.getUserId();
            String productId = reporting.getProductId();
            QueryWrapper queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id",userId);
            User user = userDao.selectOne(queryWrapper);
            if (null!=user){
                reporting.setUserId(user.getUsername());
            }
            QueryWrapper queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("id",productId);
            Product product = productDao.selectOne(queryWrapper2);
            if (null!=product){
                reporting.setProductId(product.getSname());
            }
            list2.add(reporting);
        }
        return list2;
    }

    @Override
    public String deleteById(String id) {
        UpdateWrapper updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",id);
        return reportingDao.delete(updateWrapper)>0?"处理成功":"处理失败";
    }

    @Override
    public String add(Reporting reporting) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = sdf.format(date);
        Reporting reporting1 = reporting;
        reporting1.setId(UUID.randomUUID().toString());
        reporting1.setTime(formattedDate);
        reporting1.setStatus(1);
        return reportingDao.insert(reporting1)>0?"添加成功":"添加失败";
    }

    @Override
    public String updateByStatus(Reporting reporting) {
        UpdateWrapper updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",reporting.getId());
        updateWrapper.set("status",reporting.getStatus());
        return reportingDao.update(new Reporting(),updateWrapper)>0?"修改成功":"修改失败";
    }
}
