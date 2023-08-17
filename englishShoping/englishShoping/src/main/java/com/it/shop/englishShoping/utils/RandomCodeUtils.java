package com.it.shop.englishShoping.utils;

import org.springframework.stereotype.Component;

import java.util.Random;
@Component
public class RandomCodeUtils {
    public static String generateCode() {
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            int digit = random.nextInt(10); // 生成0-9的随机数
            code.append(digit);
        }
        return code.toString();
    }
}
