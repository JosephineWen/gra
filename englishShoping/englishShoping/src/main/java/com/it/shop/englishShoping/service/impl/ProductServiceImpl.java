package com.it.shop.englishShoping.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.it.shop.englishShoping.domain.Product;
import com.it.shop.englishShoping.domain.Tags;
import com.it.shop.englishShoping.mapper.ProductDao;
import com.it.shop.englishShoping.mapper.TagsMapper;
import com.it.shop.englishShoping.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private TagsMapper tagsMapper;
    @Autowired
    private ProductDao productDao;

    @Override
    public List<Product> getList() {
        return productDao.getList();
    }

    @Override
    public Product getById(String id) {
        Product byId = productDao.getById(id);
        if (byId==null){
            byId = new Product();
        }
        return byId;
    }

    @Override
    public List<Product> getTypeList(String id) {
        List<Product> byId = productDao.getByTypeList(id);
        if (byId.size()==0){
            byId = new ArrayList<>();
        }
        return byId;
    }


    @Override
    public List<Product> getBySearch(String text) {
        String text2= "%"+text+"%";
        List<Product> byId = productDao.getBySearch(text2);
        if (byId.size()==0){
            byId = new ArrayList<>();
        }
        return byId;
    }

    @Override
    public void updateByNumber(String id, Integer number) {
        productDao.updateByNumber(id,number);
    }

    @Override
    public void updateByCode(String id, String code) {


        productDao.updateByCode(id,code);

    }

    @Override
    public boolean add(Product product) {
        Product   product1 = product;
        String s = UUID.randomUUID().toString();
         product1.setId(s);
         if (product.getTags().size()>0){
             for (String str : product.getTags()){
                 Tags tags = new Tags();
                 tags.setText(str);
                 tags.setPid(s);
                 tags.setId(UUID.randomUUID().toString());
                 tagsMapper.insert(tags);
             }
         }

     return  productDao.insert(product)>0;
    }

    @Override
    public void updateByIdToImgurl(String id, String originalFilename) {
        UpdateWrapper<Product> productUpdateWrapper = new UpdateWrapper<>();
        productUpdateWrapper.set("imgurl",originalFilename);
        productUpdateWrapper.eq("id",id);
        productDao.update(new Product(),productUpdateWrapper);
    }

    @Override
    public boolean update(Product product) {
        return productDao.updateById(product)>0;
    }

    @Override
    public List<Product> getByPriceList() {
        return productDao.getByPriceList();
    }

    @Override
    public List<Product> getByPriceList2() {
        return productDao.getByPriceList2();
    }

    @Override
    public List<Product> getBytimeList() {
        return productDao.getBytimeList();
    }

    @Override
    public List<Product> getAllList() {
        return productDao.getListAll();
    }

    @Override
    public void deleteById(String id) {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        productDao.delete(queryWrapper);
    }

    @Override
    public List<Product> listAll(String id) {
        return productDao.getListAllTwo(id);
    }

    @Override
    public List<Product> search(String search) {
        String sort = '%'+search+'%';
        QueryWrapper queryWrapper =new QueryWrapper<>();
        queryWrapper.like("sname",sort);
        List<Product> list = productDao.selectList(queryWrapper);
        List<Tags> list1 = tagsMapper.selectListBySearch(search);
        if (list1.size()>0){
            for (Tags tags : list1){
                Product product = productDao.selectById(tags.getPid());
                if (!list.contains(product)){
                    list.add(product);
                }
            }
        }
        return list;
    }

}
