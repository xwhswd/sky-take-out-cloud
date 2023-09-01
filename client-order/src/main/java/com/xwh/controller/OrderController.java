package com.xwh.controller;

import com.xwh.context.BaseContext;
import com.xwh.pojo.dto.*;
import com.xwh.pojo.result.PageResult;
import com.xwh.pojo.result.Result;
import com.xwh.pojo.vo.OrderPaymentVO;
import com.xwh.pojo.vo.OrderStatisticsVO;
import com.xwh.pojo.vo.OrderSubmitVO;
import com.xwh.pojo.vo.OrderVO;
import com.xwh.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author xwh
 * @version 1.0
 * 2023/7/29
 */
@RestController
@RequestMapping("/order")
@Api("订单信息接口")
@Slf4j
public class OrderController {
    @Resource
    private OrderService orderService;

    @Resource
    RedisTemplate redisTemplate;
    @ModelAttribute
    public void setBaseContext(HttpServletRequest request){
        BaseContext.setCurrentId((Long) redisTemplate.opsForValue().get("jwt_"+request.getHeader("token")));
    }

    @GetMapping("/conditionSearch")
    @ApiOperation("订单搜索")
    public Result<PageResult> conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO) {
        log.info("----订单信息搜索:"+ordersPageQueryDTO+"----");
        PageResult pageResult = orderService.conditionSearch(ordersPageQueryDTO);
        return Result.success(pageResult);
    }

    @GetMapping("/statistics")
    @ApiOperation("各个状态的订单数量统计")
    public Result<OrderStatisticsVO> statistics() {
        log.info("----各个状态订单数量统计--------");
        OrderStatisticsVO orderStatisticsVO = orderService.statistics();
        return Result.success(orderStatisticsVO);
    }

    @GetMapping("/details/{id}")
    @ApiOperation("查询订单详情")
    public Result<OrderVO> details(@PathVariable("id") Long id) {
        log.info("----根据id查询订单详情:"+id+"----");
        OrderVO orderVO = orderService.details(id);
        return Result.success(orderVO);
    }

    @PutMapping("/confirm")
    @ApiOperation("接单")
    public Result confirm(@RequestBody OrdersConfirmDTO ordersConfirmDTO) {
        log.info("----接收订单信息:"+ordersConfirmDTO+"----");
        orderService.confirm(ordersConfirmDTO);
        return Result.success();
    }

    @PutMapping("/rejection")
    @ApiOperation("拒单")
    public Result rejection(@RequestBody OrdersRejectionDTO ordersRejectionDTO) throws Exception {
        log.info("----拒绝接单:"+ordersRejectionDTO+"----");
        orderService.rejection(ordersRejectionDTO);
        return Result.success();
    }

    @PutMapping("/cancel")
    @ApiOperation("取消订单")
    public Result cancel(@RequestBody OrdersCancelDTO ordersCancelDTO) throws Exception {
        log.info("----拒绝接单:"+ordersCancelDTO+"----");
        orderService.cancel(ordersCancelDTO);
        return Result.success();
    }

    @PutMapping("/delivery/{id}")
    @ApiOperation("派送订单")
    public Result delivery(@PathVariable("id") Long id) {
        log.info("----派送订单:"+id+"----");
        orderService.delivery(id);
        return Result.success();
    }

    @PutMapping("/complete/{id}")
    @ApiOperation("完成订单")
    public Result complete(@PathVariable("id") Long id) {
        log.info("----完成订单:"+id+"----");
        orderService.complete(id);
        return Result.success();
    }

    @PostMapping("/submit")
    @ApiOperation("用户下单")
    public Result<OrderSubmitVO> submit(@RequestBody OrdersSubmitDTO ordersSubmitDTO){
        log.info("----用户下单:"+BaseContext.getCurrentId()+":"+ordersSubmitDTO+"----");
        OrderSubmitVO orderSubmitVO = orderService.submitOrder(ordersSubmitDTO);
        return Result.success(orderSubmitVO);
    }

    @PutMapping("/payment")
    @ApiOperation("订单支付")
    public Result<OrderPaymentVO> payment(@RequestBody OrdersPaymentDTO ordersPaymentDTO) throws Exception {
        log.info("---订单支付:"+BaseContext.getCurrentId()+":"+ordersPaymentDTO+"----");
        OrderPaymentVO orderPaymentVO = orderService.payment(ordersPaymentDTO);
        return Result.success(orderPaymentVO);
    }

    @GetMapping("/historyOrders")
    @ApiOperation("历史订单查询")
    public Result<PageResult> page(int page, int pageSize, Integer status) {
        log.info("----历史订单查询:"+BaseContext.getCurrentId()+":"+page+","+pageSize+","+status);
        log.info("订单状态 1待付款 2待接单 3已接单 4派送中 5已完成 6已取消");
        PageResult pageResult = orderService.pageQuery4User(page, pageSize, status);
        return Result.success(pageResult);
    }

    @PutMapping("/cancel/{id}")
    @ApiOperation("取消订单")
    public Result cancel(@PathVariable("id") Long id) throws Exception {
        log.info("----用户取消订单:"+ BaseContext.getCurrentId() +":"+id+"----");
        orderService.userCancelById(id);
        return Result.success();
    }

    @PostMapping("/repetition/{id}")
    @ApiOperation("再来一单")
    public Result repetition(@PathVariable Long id) {
        log.info("----用户再来一单"+ BaseContext.getCurrentId()+":"+id+"----");
        orderService.repetition(id);
        return Result.success();
    }

    @GetMapping("/reminder/{id}")
    @ApiOperation("客户催单")
    public Result reminder(@PathVariable("id") Long id){
        log.info("----客户催单"+ BaseContext.getCurrentId()+":"+id+"----");
        orderService.reminder(id);
        return Result.success();
    }

    @PostMapping("/sumByMap")
    Double sumByMap(@RequestBody Map map){
        return orderService.sumByMap(map);
    }

    @PostMapping("/countByMap")
    Integer countByMap(@RequestBody Map map){
        return orderService.countByStatus(map);
    }

    @PostMapping("/getSalesTop10")
    List<GoodsSalesDTO> getSalesTop10(@RequestBody Map<String,LocalDateTime> map){
        return orderService.getSalesTop10(map.get("start"),map.get("end"));
    }
}
