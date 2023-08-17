package com.it.shop.englishShoping.controller;

import com.it.shop.englishShoping.domain.Comments;
import com.it.shop.englishShoping.domain.Reporting;
import com.it.shop.englishShoping.service.CommentsService;
import com.it.shop.englishShoping.service.ReportingService;
import com.it.shop.englishShoping.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/comment/")
public class CommentsController {
    @Autowired
    private CommentsService service;


    @PostMapping("selectByProduct")
    public Result selectByProduct(@RequestBody Comments comments){
        log.info("查询到评论参数:{}"+comments);
        return Result.success(service.selectByProduct(comments));
    }

    @PostMapping("findByAllThree")
    public Result findByAllThree(@RequestBody Comments comments){
        log.info("评论查询到的全部参数:{}"+comments);
        return Result.success(service.findByAllThree(comments));
    }

    @PostMapping("findByAll")
    public Result findByAll(@RequestBody Comments comments){
        log.info("评论查询到的全部参数:{}"+comments);
        return Result.success(service.findByAll(comments));
    }


    @PostMapping("findByAllTwo")
    public Result findByAllTwo(@RequestBody Comments comments){
        log.info("评论查询到的全部参数:{}"+comments);
        return Result.success(service.findByAllTwo(comments));
    }

    @GetMapping("deleteById")
    public Result deleteById(@RequestParam String id){
        return Result.success(service.deleteById(id));
    }

    @GetMapping("deleteByIdTwo")
    public Result deleteByIdTwo(@RequestParam String id){
        return Result.success(service.deleteByIdTwo(id));
    }

        @PostMapping("addComm")
    public Result addComm(@RequestBody Comments comments){
        return Result.success(service.add(comments));
    }
    //
//
//    @PostMapping("updateBYStatus")
//    public Result updateBYStatus(@RequestBody Reporting reporting){
//        return Result.success(service.updateByStatus(reporting));
//    }
//
}
