package com.it.shop.englishShoping.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@TableName("shoppingcar")
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCar {
    private String id;
    private String userid;
    private String productid;
    private Integer count;

    private String status;
    
    @TableField(exist = false)
    private String sname;

    @TableField(exist = false)
    private Double price;

    @TableField(exist = false)
    private String imgurl;

    @TableField(exist = false)
    private Integer number;
}
