package com.it.shop.englishShoping.config;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class JwtInterceptor implements  HandlerInterceptor{


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
//        if (token==null){
//            return false;
//        }
//        if (token!=null){
//            return true;
//        }
//        token = token.substring(1,token.length()-1);
//        ValueOperations valueOperations = redisTemplate.opsForValue();
//        System.out.println("---------toen2-");
//        Object o1 = valueOperations.get(token);
//        System.out.println(o1);
//      throw  new RuntimeException("没有携带token");
        return true;
    }


}
