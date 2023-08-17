package com.it.shop.englishShoping.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@TableName("product")
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private String id;
    private String sname;
    private String text;
    private Double price;
    private String address;
    private String title;
    private Integer number;
    private String typeid;
    private String pinglinid;
    private String imgurl;
    private String code;

    @TableField(exist = false)
    private String tname;

    @TableField(exist = false)
    private Integer ordernumber;

    @TableField(exist = false)
    private List<String> tags;

    @TableField(exist = false)
    private String shop;
}
