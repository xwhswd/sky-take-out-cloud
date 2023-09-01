package com.xwh.service.impl;

import com.xwh.clients.DishClient;
import com.xwh.clients.OrderClient;
import com.xwh.clients.SetmealClient;
import com.xwh.clients.UserClient;
import com.xwh.constants.StatusConstant;
import com.xwh.pojo.Orders;
import com.xwh.pojo.vo.BusinessDataVO;
import com.xwh.pojo.vo.DishOverViewVO;
import com.xwh.pojo.vo.OrderOverViewVO;
import com.xwh.pojo.vo.SetmealOverViewVO;
import com.xwh.service.WorkspaceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xwh
 * @version 1.0
 * 2023/7/29
 */
@Service
public class WorkspaceServiceImpl implements WorkspaceService {
    @Resource
    private OrderClient orderClient;
    @Resource
    private UserClient userClient;
    @Resource
    private DishClient dishClient;
    @Resource
    private SetmealClient setmealClient;

    @Override
    public BusinessDataVO getBusinessData(LocalDateTime begin, LocalDateTime end) {

        Map map = new HashMap();
        map.put("begin",begin);
        map.put("end",end);

        //查询总订单数
        Integer totalOrderCount = userClient.countByMap(map);

        map.put("status", Orders.COMPLETED);
        //营业额
        Double turnover = orderClient.sumByMap(map);
        turnover = turnover == null? 0.0 : turnover;

        //有效订单数
        Integer validOrderCount = orderClient.countByMap(map);

        Double unitPrice = 0.0;

        Double orderCompletionRate = 0.0;
        if(totalOrderCount != 0 && validOrderCount != 0){
            //订单完成率
            orderCompletionRate = validOrderCount.doubleValue() / totalOrderCount;
            //平均客单价
            unitPrice = turnover / validOrderCount;
        }

        //新增用户数
        Integer newUsers = userClient.countByMap(map);

        return BusinessDataVO.builder()
                .turnover(turnover)
                .validOrderCount(validOrderCount)
                .orderCompletionRate(orderCompletionRate)
                .unitPrice(unitPrice)
                .newUsers(newUsers)
                .build();
    }

    @Override
    public OrderOverViewVO getOrderOverView() {
        Map map = new HashMap();
        map.put("begin", LocalDateTime.now().with(LocalTime.MIN));
        map.put("status", Orders.TO_BE_CONFIRMED);

        //待接单
        Integer waitingOrders = orderClient.countByMap(map);

        //待派送
        map.put("status", Orders.CONFIRMED);
        Integer deliveredOrders = orderClient.countByMap(map);

        //已完成
        map.put("status", Orders.COMPLETED);
        Integer completedOrders = orderClient.countByMap(map);

        //已取消
        map.put("status", Orders.CANCELLED);
        Integer cancelledOrders = orderClient.countByMap(map);

        //全部订单
        map.put("status", null);
        Integer allOrders = orderClient.countByMap(map);

        return OrderOverViewVO.builder()
                .waitingOrders(waitingOrders)
                .deliveredOrders(deliveredOrders)
                .completedOrders(completedOrders)
                .cancelledOrders(cancelledOrders)
                .allOrders(allOrders)
                .build();
    }

    @Override
    public SetmealOverViewVO getSetmealOverView() {
        Map map = new HashMap();
        map.put("status", StatusConstant.ENABLE);
        Integer sold = setmealClient.countByMap(map);

        map.put("status", StatusConstant.DISABLE);
        Integer discontinued = setmealClient.countByMap(map);

        return SetmealOverViewVO.builder()
                .sold(sold)
                .discontinued(discontinued)
                .build();
    }

    @Override
    public DishOverViewVO getDishOverView() {
        Map map = new HashMap();
        map.put("status", StatusConstant.ENABLE);
        Integer sold = dishClient.countByMap(map);

        map.put("status", StatusConstant.DISABLE);
        Integer discontinued = dishClient.countByMap(map);

        return DishOverViewVO.builder()
                .sold(sold)
                .discontinued(discontinued)
                .build();
    }
}
