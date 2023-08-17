package com.it.shop.englishShoping.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;


@TableName("type")
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Type {
    @TableField(value = "id")
    private String value;
    @TableField(value = "tname")
    private String text;

    @TableField(exist = false)
    public List<Product> list;
}
