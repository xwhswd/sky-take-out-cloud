package com.xwh.controller;

import com.xwh.pojo.result.Result;
import com.xwh.pojo.vo.BusinessDataVO;
import com.xwh.pojo.vo.DishOverViewVO;
import com.xwh.pojo.vo.OrderOverViewVO;
import com.xwh.pojo.vo.SetmealOverViewVO;
import com.xwh.service.WorkspaceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

/**
 * @author xwh
 * @version 1.0
 * 2023/7/29
 */
@RestController
@RequestMapping("/workspace")
@Api("工作台接口")
@Slf4j
public class WorkSpaceController {

    @Resource
    private WorkspaceService workspaceService;

    /**
     * 工作台今日数据查询
     * @return
     */
    @GetMapping("/businessData")
    @ApiOperation("工作台今日数据查询")
    public Result<BusinessDataVO> businessData(){
        log.info("----工作台今日数据查询----");
        //获得当天的开始时间
        LocalDateTime begin = LocalDateTime.now().with(LocalTime.MIN);
        //获得当天的结束时间
        LocalDateTime end = LocalDateTime.now().with(LocalTime.MAX);

        BusinessDataVO businessDataVO = workspaceService.getBusinessData(begin, end);
        return Result.success(businessDataVO);
    }

    @GetMapping("/overviewOrders")
    @ApiOperation("查询订单管理数据")
    public Result<OrderOverViewVO> orderOverView(){
        log.info("----查询订单管理数据----");
        return Result.success(workspaceService.getOrderOverView());
    }

    @GetMapping("/overviewDishes")
    @ApiOperation("查询菜品总览")
    public Result<DishOverViewVO> dishOverView(){
        log.info("----查询菜品总览----");
        return Result.success(workspaceService.getDishOverView());
    }

    @GetMapping("/overviewSetmeals")
    @ApiOperation("查询套餐总览")
    public Result<SetmealOverViewVO> setmealOverView(){
        log.info("----查询套餐总览----");
        return Result.success(workspaceService.getSetmealOverView());
    }

    @PostMapping("/stastisticMessage")
    BusinessDataVO getSomedayBusinessData(@RequestBody Map<String,LocalDateTime> map){
        return workspaceService.getBusinessData(map.get("start"),map.get("end"));
    }
}
