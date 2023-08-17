package com.it.shop.englishShoping.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@TableName("address")
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private String id;
    private String address;
    private String phone;
    private String userid;
    private String name;
    private String city;
    private String email;
}
