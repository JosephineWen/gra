package com.it.shop.englishShoping.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@TableName("orders")
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private String id;
    private String userid;
    //shops
    private String productid;

    private String kid;
    //1 Pending payment 2 Pending dispatch 3 Pending receipt 4 Pending receipt
    private String status;
    private Integer number;
    private String time;
    private Double money;
    private String addressid;
    private String expree;
    @TableField(exist = false)
    private String sname;

    @TableField(exist = false)
    private String imgurl;

    @TableField(exist = false)
    private String shopimg;

    @TableField(exist = false)
    private String shopname;

    @TableField(exist = false)
    private String shopid;

    @TableField(exist = false)
    private String time1;

    @TableField(exist = false)
    private String time2;

    @TableField(exist = false)
    private String addPhone;

    @TableField(exist = false)
    private String addText;

    @TableField(exist = false)
    private String addName;

    @TableField(exist = false)
    private String addEmail;


}
