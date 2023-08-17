package com.it.shop.englishShoping.controller;

import com.it.shop.englishShoping.domain.Reporting;
import com.it.shop.englishShoping.service.ReportingService;
import com.it.shop.englishShoping.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/report/")
public class ReportingController {

    @Autowired
    private ReportingService service;


    @GetMapping("findByAll")
    public Result findByAll(){
        return Result.success(service.findByAll());
    }



    @GetMapping("deleteById")
    public Result deleteById(@RequestParam String id){
        return Result.success(service.deleteById(id));
    }


    @PostMapping("updateBYStatus")
    public Result updateBYStatus(@RequestBody Reporting reporting){
        return Result.success(service.updateByStatus(reporting));
    }

    @PostMapping("addReporting")
    public Result addReporting(@RequestBody Reporting reporting){
        return Result.success(service.add(reporting));
    }


}
