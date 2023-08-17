package com.it.shop.englishShoping.controller;
import com.it.shop.englishShoping.domain.User;
import com.it.shop.englishShoping.service.UserService;
import com.it.shop.englishShoping.utils.Base64Util;
import com.it.shop.englishShoping.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.*;


@Slf4j
@RestController
@RequestMapping("/user/")
public class UserController {
    @Autowired
    private Base64Util base64Util;



    @Value("${dir}")
    private String imgUrl;

    @Autowired
    private UserService userService;



    @GetMapping("getCode")
    public Result findByPhone(@RequestParam String phone){
        log.info("注册:{}"+phone);
        return userService.insertPhone(phone);
    }

    @PostMapping("verifyCode")
    public Result verifyCode(@RequestBody User user){
        log.info("校验验证码:{}"+user);
        return Result.success(userService.verifyCode(user.getPhone(),user.getPhoneCode()));
    }

    @PostMapping("list")
   public Result getListUser(@RequestParam Integer code){
        log.info("进入用户请求列表操作");
        return Result.success(userService.getListUser(code));
    }

    @PostMapping("base64")
    public Result base64(@RequestBody User user){
        log.info("进入用户base64转换:{}"+user);
        String url = imgUrl+"user/";
        return Result.success(base64Util.imgs(url,user.getImgurl()));
    }

    @PostMapping("login")
     public  Result loginUser(@RequestBody User user){
        log.info("用户登录:{}"+user);
        return userService.login(user);
    }

    @PostMapping("getById")
   public Result getById(@RequestBody User user){
        log.info("获取单个用户:{}"+user);
        return Result.success(userService.getById(user.getId()));
    }

    @PostMapping("deleteById")
    public Result deleteById(@RequestBody User user){
        log.info("进入用户删除成功:{}"+user);
        userService.deleteById(user.getId());
       return Result.success("删除成功");
    }

    @PostMapping("updateStatus")
    public Result updateStatus(@RequestBody User user){
        log.info("进入用户更新:{}"+user);
        return Result.success(userService.updateStatus(user));
    }

    @PostMapping("update")
    public Result updateS(@RequestBody User user){
        log.info("进入用户更新:{}"+user);
        return Result.success(userService.updateById(user));
    }

    @PostMapping("add")
    public Result  newUserAdd(@RequestBody User user){
        log.info("进入用户添加:{}"+user);
        return Result.success(userService.add(user));
    }

    @PostMapping("addTwo")
    public Result  addTwo(@RequestBody User user){
        log.info("进入用户添加:{}"+user);
        return Result.success(userService.addTwo(user));
    }

    @PostMapping("updateByMoney")
    public Result updateByMoney(@RequestBody User user){
        log.info("进入用户base64转换:{}"+user);
        return Result.success(userService.updateByMoney(user.getId(),user.getMoney()));
    }

    @PostMapping("upload")
    public Result uploadByImage(@Param("file") MultipartFile file) throws IOException {
        log.info("图片上传操作:{}");
        //获取文件名
        String originalFilename = file.getOriginalFilename();
        String substring = originalFilename.substring(originalFilename.lastIndexOf("."));
        originalFilename= UUID.randomUUID()+substring;
        //路径名
        File file1 = new File(imgUrl+"user/"+originalFilename);
        file.transferTo(file1);
        return Result.success(originalFilename);
    }

    @PostMapping("upload2")
    public Result uploadByImage2(@Param("file") MultipartFile file,@Param("id") String id) throws IOException {
        log.info("图片上传且更新数据库操作:{}");
        //获取文件名
        String originalFilename = file.getOriginalFilename();
        String substring = originalFilename.substring(originalFilename.lastIndexOf("."));
        originalFilename= UUID.randomUUID()+substring;
        //路径名
        File file1 = new File(imgUrl+"user/"+originalFilename);
        file.transferTo(file1);
        userService.updateByIdToImgurl(id,originalFilename);
        return Result.success(originalFilename);
    }





}
