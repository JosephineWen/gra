package com.it.shop.englishShoping.service;

import com.it.shop.englishShoping.domain.Comments;
import com.it.shop.englishShoping.domain.Reporting;

import java.util.List;

public interface CommentsService {

    List<Comments> findByAll(Comments comments);
    List<Comments> findByAllTwo(Comments comments);

    String deleteById(String id);

    String add(Comments Comments);

    String updateByStatus(Comments Comments);

     String intoText(String text);

    String deleteByIdTwo(String id);

    List<Comments> findByAllThree(Comments comments);

    List<Comments> selectByProduct(Comments comments);
}
