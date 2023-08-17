package com.it.shop.englishShoping.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@TableName("reporting")
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reporting {


    @TableField("id")
    private String id;

    @TableField("text")
    private String text;

    @TableField("user_id")
    private String userId;

    @TableField("product_id")
    private String productId;

    @TableField("status")
    private Integer status;

    @TableField("time")
    private String time;

}
