package com.it.shop.englishShoping.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("comments")
public class Comments {

    @TableField("id")
    private String id;

    @TableField("text")
    private String text;

    @TableField("user_id")
    private String userId;

    @TableField("product_id")
    private String productId;

    @TableField("sort")
    private Integer sort;

    @TableField("parent")
    private String parent;

    @TableField("time")
    private String time;

    @TableField("status")
    private Integer status;


    @TableField(exist = false)
    private Comments list;



    @TableField(exist = false)
    private String shopId;

    @TableField(exist = false)
    private String sort2;

    @TableField(exist = false)
    private String userName;

    @TableField(exist = false)
    private String shop;

}
