package com.it.shop.englishShoping.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@TableName("key_words")
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Keywords {
    private Integer id;
    private String text;
}
