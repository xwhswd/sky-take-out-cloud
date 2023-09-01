package com.xwh.controller;

import com.xwh.pojo.Dish;
import com.xwh.pojo.dto.DishDTO;
import com.xwh.pojo.dto.DishPageQueryDTO;
import com.xwh.pojo.result.PageResult;
import com.xwh.pojo.result.Result;
import com.xwh.pojo.vo.DishItemVO;
import com.xwh.pojo.vo.DishVO;
import com.xwh.service.DishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author xwh
 * @version 1.0
 * 2023/7/29
 */
@RestController
@RequestMapping("/dish")
@Api("菜品信息接口")
@Slf4j
public class DishController {
    @Resource
    private DishService dishService;

    @PostMapping
    @ApiOperation("新增菜品")
    public Result save(@RequestBody DishDTO dishDTO) {
        log.info("----正在新增菜品:"+dishDTO+"----");
        dishService.save(dishDTO);
        return Result.success();
    }


    @GetMapping("/page")
    @ApiOperation("菜品分页查询")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO) {
        log.info("----菜品分页查询:"+dishPageQueryDTO+"----");
        PageResult page = dishService.page(dishPageQueryDTO);
        return Result.success(page);
    }

    @DeleteMapping
    @ApiOperation("菜品批量删除")
    public Result delete(@RequestParam List<Long> ids) {
        log.info("----菜品批量删除:"+ids+"----");
        boolean delete = dishService.delete(ids);
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查询菜品")
    public Result<DishVO> getById(@PathVariable Long id) {
        log.info("----根据id查询菜品:"+id+"----");
        DishVO byId = dishService.getById(id);
        return Result.success(byId);
    }

    @PutMapping
    @ApiOperation("修改菜品")
    public Result update(@RequestBody DishDTO dishDTO) {
        log.info("----根据id修改菜品:"+dishDTO+"----");
        boolean update = dishService.update(dishDTO);
        return Result.success(update);
    }

    @PostMapping("/status/{status}")
    @ApiOperation("菜品起售停售")
    public Result<String> startOrStop(@PathVariable Integer status, Long id) {
        log.info("----菜品起售停售"+id+":"+status+"----");
        boolean b = dishService.startOrStop(status, id);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("根据分类id查询菜品")
    public Result<List<Dish>> list(Long categoryId) {
        log.info("----根据分类id查询菜品:"+categoryId+"----");
        List<Dish> list = dishService.list(categoryId);
        return Result.success(list);
    }

    @GetMapping("/dishList")
    public List<DishItemVO> dishList(Long id) {
        log.info("----根据分类id查询菜品:"+id+"----");
        List<DishItemVO> dishItemVOS = dishService.dishList(id);
        return dishItemVOS;
    }

    @PostMapping("/getById/{id}")
    Dish getDishById(@PathVariable("id") Long dishId){
        return dishService.getByDishId(dishId);
    }



    @PostMapping("/count")
    Integer countByMap(@RequestBody Map map){
        return dishService.countByStatus(map);
    }
    /**
     * 清理缓存数据
     * @param pattern
     */
    private void cleanCache(String pattern){

    }
}
