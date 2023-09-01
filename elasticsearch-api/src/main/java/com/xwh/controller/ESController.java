package com.xwh.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xwh
 * @version 1.0
 * 2023/7/29
 */
@RestController
@RequestMapping("/es")
@Api("ES搜索引擎")
@Slf4j
public class ESController {
    @RequestMapping("/es_update")
    @ApiOperation("es更新")
    public void update(){
        log.info("----es更新----");
    }

    @RequestMapping("/es_insert")
    @ApiOperation("es插入")
    public void insert(){
        log.info("----es插入----");
    }

    @RequestMapping("/es_delete")
    @ApiOperation("es删除")
    public void delete(){
        log.info("----es删除----");
    }

    @RequestMapping("/selectAll")
    @ApiOperation("es查询全部")
    public void selectAll(){
        log.info("----es查询全部----");
    }

}
