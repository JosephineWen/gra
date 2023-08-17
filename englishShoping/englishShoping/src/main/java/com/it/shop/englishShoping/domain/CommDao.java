package com.it.shop.englishShoping.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommDao {

    private String id;

    private String text;

    private String userId;

    private String productId;


    private Integer sort;


    private String parent;


    private String time;






    private String shopId;
}
