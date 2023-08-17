package com.it.shop.englishShoping.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@TableName("user")
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String id;
    private String username;
    private String password;
    private String name;
    private String age;
    private String phone;
    private String code;
    private String imgurl;
    private Double money;
    //1.允许登录 //2.禁止登录
    private Integer status;

    @TableField(exist = false)
    private String shop;

    @TableField(exist = false)
    private String phoneCode;


}
