package com.xwh.controller;

import com.xwh.constants.StatusConstant;
import com.xwh.pojo.Setmeal;
import com.xwh.pojo.dto.SetmealDTO;
import com.xwh.pojo.dto.SetmealPageQueryDTO;
import com.xwh.pojo.result.PageResult;
import com.xwh.pojo.result.Result;
import com.xwh.pojo.vo.DishItemVO;
import com.xwh.pojo.vo.SetmealVO;
import com.xwh.service.SetmealService;
import com.xwh.service.impl.SetmealServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author xwh
 * @version 1.0
 * 2023/7/29
 */
@RestController
@RequestMapping("/setmeal")
@Api("套餐接口")
@Slf4j
public class SetmealController {
    @Resource
    private SetmealService setmealService;

    @GetMapping("/list")
    @ApiOperation("根据分类id查询套餐")
    @Cacheable(cacheNames = "setmealCache",key = "#categoryId") //key: setmealCache::100
    public Result<List<Setmeal>> list(Long categoryId) {
        log.info("----根据分类id查询套餐:"+categoryId+"----");
        Setmeal setmeal = new Setmeal();
        setmeal.setCategoryId(categoryId);
        setmeal.setStatus(StatusConstant.ENABLE);
        List<Setmeal> list = setmealService.list(setmeal);
        return Result.success(list);
    }

    @GetMapping("/dish/{id}")
    @ApiOperation("根据套餐id查询包含的菜品列表")
    public Result<List<DishItemVO>> dishList(@PathVariable("id") Long id) {
        log.info("----根据套餐id查询包含的菜品列表:"+id+"----");
        List<DishItemVO> list = setmealService.getDishItemById(id);
        return Result.success(list);
    }

    @PostMapping
    @ApiOperation("新增套餐")
    @CacheEvict(cacheNames = "setmealCache",key = "#setmealDTO.categoryId")//key: setmealCache::100
    public Result save(@RequestBody SetmealDTO setmealDTO) {
        log.info("----新增套餐:"+setmealDTO+"----");
        setmealService.saveWithDish(setmealDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("分页查询")
    public Result<PageResult> page(SetmealPageQueryDTO setmealPageQueryDTO) {
        log.info("----分页查询:"+setmealPageQueryDTO+"----");
        PageResult pageResult = setmealService.pageQuery(setmealPageQueryDTO);
        return Result.success(pageResult);
    }

    @DeleteMapping
    @ApiOperation("批量删除套餐")
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result delete(@RequestParam List<Long> ids) {
        log.info("----批量删除套餐:"+ids+"----");
        setmealService.deleteBatch(ids);
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查询套餐")
    public Result<SetmealVO> getById(@PathVariable Long id) {
        log.info("----根据id查询套餐，用于修改页面回显数据:"+id+"----");
        SetmealVO setmealVO = setmealService.getByIdWithDish(id);
        return Result.success(setmealVO);
    }

    @PutMapping
    @ApiOperation("修改套餐")
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result update(@RequestBody SetmealDTO setmealDTO) {
        log.info("----修改套餐:"+setmealDTO+"----");
        setmealService.update(setmealDTO);
        return Result.success();
    }

    @PostMapping("/status/{status}")
    @ApiOperation("套餐起售停售")
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result startOrStop(@PathVariable Integer status, Long id) {
        log.info("----套餐起售停售:"+id+","+status+"----");
        setmealService.startOrStop(status, id);
        return Result.success();
    }

    @PostMapping("/getById/{id}")
    public Setmeal getSetMealById(@PathVariable("id") Long setmealId){
        return setmealService.getSeatMealById(setmealId);
    }

    @PostMapping("/count")
    Integer countByMap(@RequestBody Map map){
        return setmealService.countByStatus(map);
    }
}
