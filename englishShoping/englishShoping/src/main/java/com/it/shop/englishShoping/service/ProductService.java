package com.it.shop.englishShoping.service;
import com.it.shop.englishShoping.domain.Product;

import java.util.List;

public interface ProductService {
    List<Product> getList();

    Product getById( String id);

    List<Product> getTypeList(String id);

    List<Product> getBySearch(String text);

    public void updateByNumber(String id, Integer number);

    void updateByCode(String id, String code);

    boolean add(Product product);

    void updateByIdToImgurl(String id, String originalFilename);

    boolean update(Product product);

    List<Product> getByPriceList();

    List<Product> getByPriceList2();

    List<Product> getBytimeList();

    List<Product> getAllList();

    void deleteById(String id);

    List<Product> listAll(String id);

    List<Product> search(String search);
}
