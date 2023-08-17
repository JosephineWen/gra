package com.it.shop.englishShoping.utils;
import java.util.List;

public class PaginationUtils {
    public static <T> List<T> getPage(List<T> list, int pageSize, int currentPage) {
        int startIndex = (currentPage - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, list.size());
        return list.subList(startIndex, endIndex);
    }
    public static int getTotalPage(List<?> list, int pageSize) {
        return (int) Math.ceil((double) list.size() / pageSize);
    }
}
