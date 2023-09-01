package com.xwh.service;

import com.xwh.pojo.dto.*;
import com.xwh.pojo.result.PageResult;
import com.xwh.pojo.vo.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author xwh
 * @version 1.0
 * 2023/7/29
 */
public interface OrderService {

    /**
     * 订单条件查询
     * @param ordersPageQueryDTO
     * @return
     */
    PageResult conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     * 各个状态的订单数量统计
     * @return
     */
    OrderStatisticsVO statistics();

    /**
     * 订单详情
     * @param id
     * @return
     */
    OrderVO details(Long id);

    /**
     * 接单
     * @param ordersConfirmDTO
     * @return
     */
    boolean confirm(OrdersConfirmDTO ordersConfirmDTO);

    /**
     * 拒单
     * @param ordersRejectionDTO
     * @return
     */
    boolean rejection(OrdersRejectionDTO ordersRejectionDTO);

    /**
     * 取消订单
     * @param ordersCancelDTO
     * @return
     */
    boolean cancel(OrdersCancelDTO ordersCancelDTO);

    /**
     * 派送订单
     * @param id
     * @return
     */
    boolean delivery(Long id);

    /**
     * 完成订单
     * @param id
     * @return
     */
    boolean  complete(Long id);

    /**
     * 用户下单
     * @param ordersSubmitDTO
     * @return
     */
    boolean submit(OrdersSubmitDTO ordersSubmitDTO);

    /**
     * 订单支付
     * @param ordersPaymentDTO
     * @return
     */
    OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO);

    /**
     * 历史订单查询
     * @param page
     * @param pageSize
     * @param status
     * @return
     */
    PageResult page(int page, int pageSize, Integer status);

    /**
     * 用户取消订单
     * @param id
     * @return
     */
    boolean cancel(Long id);

    /**
     * 再来一单
     * @param id
     * @return
     */
    boolean repetition(Long id);

    /**
     * 用户催单
     * @param id
     * @return
     */
    boolean reminder(Long id);

    OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);

    PageResult pageQuery4User(int page, int pageSize, Integer status);

    void userCancelById(Long id);

    Double sumByMap(Map map);

    Integer countByStatus(Map map);

    List<GoodsSalesDTO> getSalesTop10(LocalDateTime begin, LocalDateTime end);
}
