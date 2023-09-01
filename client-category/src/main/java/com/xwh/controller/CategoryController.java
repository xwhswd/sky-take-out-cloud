package com.xwh.controller;

import com.xwh.pojo.Category;
import com.xwh.pojo.dto.CategoryPageQueryDTO;
import com.xwh.pojo.dto.CategoryDTO;
import com.xwh.pojo.result.PageResult;
import com.xwh.pojo.result.Result;
import com.xwh.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xwh
 * @version 1.0
 * 2023/7/29
 */
@RestController
@RequestMapping("/category")
@Api("分类信息接口")
@Slf4j
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    @PostMapping
    @ApiOperation("新增分类")
    public Result<String> save(@RequestBody CategoryDTO categoryDTO){
        log.info("----新增分类信息:"+categoryDTO+"----");
        categoryService.save(categoryDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("分类分页查询")
    public Result<PageResult> page(CategoryPageQueryDTO categoryPageQueryDTO){
        log.info("----分页查询分类信息:"+categoryPageQueryDTO+"----");
        PageResult page = categoryService.page(categoryPageQueryDTO);
        return Result.success(page);
    }

    @DeleteMapping
    @ApiOperation("删除分类")
    public Result<String> deleteById(Long id){
        log.info("----删除分类信息:"+id+"----");
        categoryService.deleteById(id);
        return Result.success();
    }

    @PutMapping
    @ApiOperation("修改分类")
    public Result<String> update(@RequestBody CategoryDTO categoryDTO){
        log.info("----修改分类信息:"+categoryDTO+"----");
        categoryService.update(categoryDTO);
        return Result.success();
    }

    @PostMapping("/status/{status}")
    @ApiOperation("启用禁用分类")
    public Result<String> startOrStop(@PathVariable("status") Integer status, Long id){
        log.info("----启用禁用分类:"+id+":"+status);
        categoryService.startOrStop(status,id);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("根据类型查询分类")
    public Result<List<Category>> list(Integer type){
        log.info("----根据类型查询分类:"+type+"----");
        List<Category> categories = categoryService.listByCate(type);
        return Result.success(categories);
    }

    @PostMapping("/getById/{id}")
    @ApiOperation("根据id查询分类")
    public Category getById(@PathVariable("id")Long id){
        log.info("----根据id查询分类信息:"+id+"----");
        return categoryService.getByID(id);
    }
}
