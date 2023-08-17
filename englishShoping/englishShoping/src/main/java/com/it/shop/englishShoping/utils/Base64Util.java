package com.it.shop.englishShoping.utils;

import org.springframework.stereotype.Component;
import sun.misc.BASE64Encoder;

import java.io.*;

@Component
public class Base64Util {

    public String imgs(String imgurl,String imgName) {
        InputStream in = null;
        final ByteArrayOutputStream data = new ByteArrayOutputStream();
        //读取图片字节数组
        try {
            String url = imgurl+imgName;
            final byte[] by = new byte[1024];
            InputStream inputStream = new FileInputStream(new File( url));
            int len = -1;
            while ((len = inputStream.read(by)) != -1) {
                data.write(by, 0, len);
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        //返回Base64编码过的字节数组字符串
        String encode = encoder.encode(data.toByteArray());
        /*encode = encode.replaceAll("[\\s*\t\n\r]", "");*/
        return encode;
    }

}
