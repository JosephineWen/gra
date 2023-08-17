package com.it.shop.englishShoping.utils;
import org.apache.commons.codec.digest.DigestUtils;


public class MD5Utils {
    public static String encrypt(String text) {
        return DigestUtils.md5Hex(text);
    }
}
