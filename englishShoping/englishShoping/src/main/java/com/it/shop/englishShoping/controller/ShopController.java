package com.it.shop.englishShoping.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.it.shop.englishShoping.domain.Product;
import com.it.shop.englishShoping.domain.Shop;
import com.it.shop.englishShoping.domain.User;
import com.it.shop.englishShoping.mapper.ShopDao;
import com.it.shop.englishShoping.service.ShopService;
import com.it.shop.englishShoping.service.UserService;
import com.it.shop.englishShoping.utils.Base64Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    ShopDao shopDao;
    @Value("${dir}")
    private String fileUrl;

    @Autowired
    private ShopService service;

    @Autowired
    private UserService userService;
    @Autowired
    private Base64Util base64Util;

    @PostMapping("/list")
    List<Product> getByShopList(@RequestBody Product product){
      return  service.getByShopList(product.getPinglinid());
    }

    @PostMapping("/base64")
    public String base64(@RequestBody Shop shop){
        String url = fileUrl;
        return base64Util.imgs(url,shop.getImgurl());
    }

    @PostMapping("/getByShopId")
    Shop getByShopId(@RequestBody Shop shop){
        return  service.getByShopId(shop.getId());
    }

    @PostMapping("/update")
    boolean update(@RequestBody Shop shop){
        return  service.update(shop);
    }

    @PostMapping("/add")
    boolean add(@RequestBody Shop shop){
        return  service.add(shop);
    }

    @PostMapping("/getById")
    Shop getById(@RequestBody User user){
        return  service.getByUser(user.getId());
    }

    @PostMapping("/upload2")
    public String uploadByImage2(@Param("file") MultipartFile file, @Param("id") String id) throws IOException {
        //获取文件名
        String originalFilename = file.getOriginalFilename();
        String substring = originalFilename.substring(originalFilename.lastIndexOf("."));
        originalFilename= UUID.randomUUID()+substring;
        //路径名
        File file1 = new File(fileUrl+"/shop/"+originalFilename);
        file.transferTo(file1);
        service.updateByIdToImgurl(id,originalFilename);
        return originalFilename;
    }

    @PostMapping("/shopList")
    public List shopList() {
        return  shopDao.selectList(new QueryWrapper<>());
    }

}
