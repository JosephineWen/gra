package com.it.shop.englishShoping.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@TableName("shop")
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Shop {
    private String id;
    private String userid;
    private String name;
    private String imgurl;
}
