package com.it.shop.englishShoping.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.it.shop.englishShoping.domain.*;
import com.it.shop.englishShoping.mapper.TagsMapper;
import com.it.shop.englishShoping.mapper.TypeDao;
import com.it.shop.englishShoping.service.ProductService;
import com.it.shop.englishShoping.utils.Base64Util;
import com.it.shop.englishShoping.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/product")
public class ProductController {
    @Value("${dir}")
    private String imgUrl;
    @Autowired
    private ProductService productService;

    @Autowired
    private Base64Util base64Util;

    @Autowired
    private TypeDao typeDao;

    @Autowired
    private TagsMapper tagsMapper;

    @PostMapping("/timeList")
    public Result getBytimeList(){
        return Result.success( productService.getBytimeList());
    }

    @PostMapping("/list")
    public List<Product> getByList(){
        List<Product> list = productService.getList();
        return list;

    }

    @PostMapping("/searchTwo")
    public Result searchTwo(@RequestParam String search){
        List<Product> list = productService.search(search);
        return Result.success(list);

    }

    @GetMapping("/listAll")
    public Result listAll(){
        List<Product> list = productService.getAllList();
        return Result.success(list);
    }

    @PostMapping("/listByShopId")
    public Result listAll(@RequestBody Shop shop){
        List<Product> list = productService.listAll(shop.getId());
        return Result.success(list);
    }

    @PostMapping("/base64")
    public String base64(@RequestBody Product product){
        String url = imgUrl;
        return base64Util.imgs(url,product.getImgurl());
    }

    @PostMapping("/priceList")
    public List<Product> getByPriceList(){
        return productService.getByPriceList();
    }

    @PostMapping("/priceList2")
    public List<Product> getByPriceList2(){
        return productService.getByPriceList2();
    }


    @PostMapping("/getById")
    public Result getByList(@RequestBody Product product){
         return Result.success( productService.getById(product.getId()));
    }

    @PostMapping("/byTypelist")
    public Result getByTypeList(@RequestBody Product product){
        return Result.success(productService.getTypeList(product.getTypeid()));
    }
    @PostMapping("/search")
    public List<Product> getBySearch(@RequestBody Product product){
        if (product.getText().equals("")){
            return  new ArrayList<>();
        }
        return productService.getBySearch(product.getText());
    }
    @PostMapping("/updateByCode")
    public void  updateByCode(@RequestBody Product product){
        productService.updateByCode(product.getId(),product.getCode());
    }

    @PostMapping("/add")
    public boolean  add(@RequestBody Product product){
      return   productService.add(product);
    }

    @GetMapping("/deleteByPid")
    public Result delete(@RequestParam String id){
        productService.deleteById(id);
        return Result.success();
    }

    @PostMapping("/update")
    public boolean  update(@RequestBody Product product){
        return   productService.update(product);
    }

    @PostMapping("/upload")
    public String uploadByImage(@Param("file") MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String substring = originalFilename.substring(originalFilename.lastIndexOf("."));
        originalFilename= UUID.randomUUID()+substring;
        File file1 = new File(imgUrl+originalFilename);
        file.transferTo(file1);
        return originalFilename;
    }

    @PostMapping("/upload2")
    public String uploadByImage2(@Param("file") MultipartFile file,@Param("id") String id) throws IOException {
        // 将图片转换为字节数组
        byte[] imageData = file.getBytes();
        // 创建RestTemplate对象
        RestTemplate restTemplate = new RestTemplate();
        // 设置请求头，指定Content-Type为multipart/form-data
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        // 创建请求体，将图片数据添加到请求体中
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("image", new ByteArrayResource(imageData) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        });
        // 创建请求对象
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        // 发送POST请求给Flask服务
        String flaskUrl = "http://127.0.0.1:5000/predict";
        ResponseEntity<String> responseEntity = restTemplate.exchange(flaskUrl, HttpMethod.POST, requestEntity, String.class);
        // 获取Flask服务的响应
        String response = responseEntity.getBody();
        String trimmedStr = response.replaceAll("^\\[|\\]$", "");
        String[] strArray = trimmedStr.split(",");
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid",id);
        tagsMapper.delete(queryWrapper);
        // 将字符串数组转换为列表
        List<String> list = Arrays.asList(strArray);
        List<String> tags = new ArrayList<>();
        for (String str : list){
            String s = str.replaceAll("^\"|\"$|\\n", "");
            tags.add(s);
            Tags tags1 = new Tags();
            tags1.setId(UUID.randomUUID().toString());
            tags1.setPid(id);
            tags1.setText(s);
            tagsMapper.insert(tags1);
        }
        //获取文件名
        String originalFilename = file.getOriginalFilename();
        String substring = originalFilename.substring(originalFilename.lastIndexOf("."));
        originalFilename= UUID.randomUUID()+substring;
        //路径名
        File file1 = new File(imgUrl+originalFilename);
        file.transferTo(file1);
        productService.updateByIdToImgurl(id,originalFilename);
        return originalFilename;
    }

    @PostMapping("/typeList")
    public List<Type> getTypeByList(){
        return typeDao.selectList(new QueryWrapper<>());
    }
    @PostMapping("/typeListTwo")
    public List<Type> typeListTwo(){
        List<Type> types = typeDao.selectList(new QueryWrapper<>());
        List<Type> arrayList = new ArrayList<>();
        for (Type type : types){
            List<Product> typeList = productService.getTypeList(type.getValue());
            if (typeList.size()>0){
                type.setList(typeList);
            }
            arrayList.add(type);
        }
        return arrayList;
    }

    @PostMapping("/typeAdd")
    public Result add(@RequestBody Type type){
        if (StringUtils.isNotEmpty(type.getValue())){
            UpdateWrapper updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id",type.getValue());
            updateWrapper.set("tname",type.getText());
            typeDao.update(new Type(),updateWrapper);
        }else{
            Type type1 = type;
            type1.setValue(UUID.randomUUID().toString());
            typeDao.insert(type1);
        }
        return Result.success();
    }

    @GetMapping("/deleteById")
    public Result deleteById(@RequestParam String id){
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        System.out.println( typeDao.delete(queryWrapper));
        return Result.success();
    }



//    @PostMapping("/upload3")
//    public String upload3(@Param("file") MultipartFile file) throws IOException {
//        //获取文件名
//        String originalFilename = file.getOriginalFilename();
//        String substring = originalFilename.substring(originalFilename.lastIndexOf("."));
//        originalFilename= UUID.randomUUID()+substring;
//        //路径名
//        File file1 = new File(imgUrl+originalFilename);
//        file.transferTo(file1);
//        return uploadImage(file);
//    }

    @PostMapping("/upload3")
    public Product uploadImage(@Param("file") MultipartFile file) throws IOException {
        // 将图片转换为字节数组
        byte[] imageData = file.getBytes();
        // 创建RestTemplate对象
        RestTemplate restTemplate = new RestTemplate();
        // 设置请求头，指定Content-Type为multipart/form-data
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        // 创建请求体，将图片数据添加到请求体中
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("image", new ByteArrayResource(imageData) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        });
        // 创建请求对象
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        // 发送POST请求给Flask服务
        String flaskUrl = "http://127.0.0.1:5000/predict";
        ResponseEntity<String> responseEntity = restTemplate.exchange(flaskUrl, HttpMethod.POST, requestEntity, String.class);
        // 获取Flask服务的响应
        String response = responseEntity.getBody();
        String trimmedStr = response.replaceAll("^\\[|\\]$", "");
        String[] strArray = trimmedStr.split(",");
        // 将字符串数组转换为列表
        List<String> list = Arrays.asList(strArray);
        List<String> tags = new ArrayList<>();
        for (String str : list){
         tags.add(str.replaceAll("^\"|\"$|\\n", ""));
        }
        Product user = new Product();
        user.setTags(tags);
        String originalFilename = file.getOriginalFilename();
        String substring = originalFilename.substring(originalFilename.lastIndexOf("."));
        originalFilename= UUID.randomUUID()+substring;
        File file1 = new File(imgUrl+originalFilename);
        file.transferTo(file1);
        user.setImgurl( originalFilename);
        return user;
    }

}
