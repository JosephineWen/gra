package com.it.shop.englishShoping.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@TableName("tags")
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tags {
    private String id;
    private String pid;
    private String text;
}
