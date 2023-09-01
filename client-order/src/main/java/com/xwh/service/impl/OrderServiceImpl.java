package com.xwh.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xwh.clients.AddressClient;
import com.xwh.clients.ShoppingCartClient;
import com.xwh.clients.UserClient;
import com.xwh.constants.MessageConstant;
import com.xwh.context.BaseContext;
import com.xwh.mapper.OrderDetailMapper;
import com.xwh.mapper.OrderMapper;
import com.xwh.pojo.*;
import com.xwh.pojo.dto.*;
import com.xwh.pojo.result.PageResult;
import com.xwh.pojo.vo.*;
import com.xwh.service.OrderService;
import com.xwh.util.WeChatPayUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author xwh
 * @version 1.0
 * 2023/7/29
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private OrderDetailMapper orderDetailMapper;
    @Resource
    private AddressClient addressClient;
    @Resource
    private ShoppingCartClient shoppingCartClient;
    @Resource
    private UserClient userClient;
    @Resource
    private WeChatPayUtil weChatPayUtil;


    @Override
    public PageResult conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO) {
        QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();
        if (ordersPageQueryDTO.getNumber()!=null){
            queryWrapper.eq("number",ordersPageQueryDTO.getNumber());
        }
        if (ordersPageQueryDTO.getPhone()!=null){
            queryWrapper.eq("phone",ordersPageQueryDTO.getPhone());
        }
        if (ordersPageQueryDTO.getStatus()!=null){
            queryWrapper.eq("status",ordersPageQueryDTO.getStatus());
        }
        if (ordersPageQueryDTO.getBeginTime()!=null){
            queryWrapper.ge("order_time",ordersPageQueryDTO.getBeginTime());
        }
        if (ordersPageQueryDTO.getEndTime()!=null){
            queryWrapper.le("order_time",ordersPageQueryDTO.getEndTime());
        }
        if (ordersPageQueryDTO.getUserId()!=null){
            queryWrapper.eq("user_id",ordersPageQueryDTO.getUserId());
        }
        Integer count = orderMapper.selectCount(queryWrapper);
        if (ordersPageQueryDTO.getPageSize()!=0){
            int pageNum = ordersPageQueryDTO.getPage();
            int pageSize = ordersPageQueryDTO.getPageSize();
            queryWrapper.last("limit "+(pageNum-1)*pageSize+","+pageSize);
        }
        List<Orders> orders = orderMapper.selectList(queryWrapper);
        ArrayList<OrderVO> orderVOS = new ArrayList<>();
        if (!CollectionUtils.isEmpty(orders)) {
            for (Orders order : orders) {
                // 将共同字段复制到OrderVO
                OrderVO orderVO = new OrderVO();
                BeanUtils.copyProperties(order, orderVO);
                String orderDishes = getOrderDishesStr(order);
                // 将订单菜品信息封装到orderVO中，并添加到orderVOList
                orderVO.setOrderDishes(orderDishes);
                orderVOS.add(orderVO);
            }
        }
        PageResult pageResult = new PageResult(count, orderVOS);
        return pageResult;
    }

    private String getOrderDishesStr(Orders orders) {
        QueryWrapper<OrderDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id",orders.getId());
        // 查询订单菜品详情信息（订单中的菜品和数量）
        List<OrderDetail> orderDetailList = orderDetailMapper.selectList(queryWrapper);

        // 将每一条订单菜品信息拼接为字符串（格式：宫保鸡丁*3；）
        List<String> orderDishList = orderDetailList.stream().map(x -> {
            String orderDish = x.getName() + "*" + x.getNumber() + ";";
            return orderDish;
        }).collect(Collectors.toList());

        // 将该订单对应的所有菜品信息拼接在一起
        return String.join("", orderDishList);
    }
    @Override
    public OrderStatisticsVO statistics() {
        Integer readyToConfirm = orderMapper.selectCount(new QueryWrapper<Orders>().eq("status", Orders.TO_BE_CONFIRMED));
        Integer confirmed = orderMapper.selectCount(new QueryWrapper<Orders>().eq("status", Orders.CONFIRMED));
        Integer delivery = orderMapper.selectCount(new QueryWrapper<Orders>().eq("status", Orders.DELIVERY_IN_PROGRESS));
        OrderStatisticsVO orderStatisticsVO = new OrderStatisticsVO();
        orderStatisticsVO.setConfirmed(confirmed);
        orderStatisticsVO.setToBeConfirmed(readyToConfirm);
        orderStatisticsVO.setDeliveryInProgress(delivery);
        return orderStatisticsVO;
    }

    @Override
    public OrderVO details(Long id) {
        // 根据id查询订单
        Orders orders = orderMapper.selectById(id);

        // 查询该订单对应的菜品/套餐明细
        List<OrderDetail> orderDetailList = orderDetailMapper.
                selectList(new QueryWrapper<OrderDetail>().eq("order_id",id));

        // 将该订单及其详情封装到OrderVO并返回
        OrderVO orderVO = new OrderVO();
        BeanUtils.copyProperties(orders, orderVO);
        orderVO.setOrderDetailList(orderDetailList);

        return orderVO;
    }

    @Override
    public boolean confirm(OrdersConfirmDTO ordersConfirmDTO) {
        Orders orders = orderMapper.selectById(ordersConfirmDTO.getId());
        orders.setStatus(ordersConfirmDTO.getStatus());
        int i = orderMapper.updateById(orders);
        return i>0;
    }

    @Override
    public boolean rejection(OrdersRejectionDTO ordersRejectionDTO) {
        Orders orders = orderMapper.selectById(ordersRejectionDTO.getId());
        orders.setStatus(Orders.CANCELLED);
        orders.setRejectionReason(ordersRejectionDTO.getRejectionReason());
        int i = orderMapper.updateById(orders);
        return i>0;
    }

    @Override
    public boolean cancel(OrdersCancelDTO ordersCancelDTO) {
        Orders orders = orderMapper.selectById(ordersCancelDTO.getId());
        orders.setStatus(Orders.CANCELLED);
        orders.setCancelReason(ordersCancelDTO.getCancelReason());
        int i = orderMapper.updateById(orders);
        return i>0;
    }

    @Override
    public boolean delivery(Long id) {
        Orders orders = orderMapper.selectById(id);
        orders.setStatus(Orders.DELIVERY_IN_PROGRESS);
        int i = orderMapper.updateById(orders);
        return i>0;
    }

    @Override
    public boolean complete(Long id) {
        Orders orders = orderMapper.selectById(id);
        orders.setStatus(Orders.COMPLETED);
        int i = orderMapper.updateById(orders);
        return i>0;
    }

    @Override
    public boolean submit(OrdersSubmitDTO ordersSubmitDTO) {
        AddressBook addressBook = addressClient.getAddressById(ordersSubmitDTO.getAddressBookId());
        if(addressBook == null){
            //抛出业务异常
            throw new RuntimeException(MessageConstant.ADDRESS_BOOK_IS_NULL);
        }
        Long userId = BaseContext.getCurrentId();
        return false;
    }

    @Override
    public OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) {
        // 当前登录用户id
        Long userId = BaseContext.getCurrentId();
        User user = userClient.getById(userId);

        //调用微信支付接口，生成预支付交易单
        JSONObject jsonObject = null;
        try {
            jsonObject = weChatPayUtil.pay(
                    ordersPaymentDTO.getOrderNumber(), //商户订单号
                    new BigDecimal(0.01), //支付金额，单位 元
                    "苍穹外卖订单", //商品描述
                    user.getOpenid() //微信用户的openid
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (jsonObject.getString("code") != null && jsonObject.getString("code").equals("ORDERPAID")) {
            throw new RuntimeException("该订单已支付");
        }

        OrderPaymentVO vo = jsonObject.toJavaObject(OrderPaymentVO.class);
        vo.setPackageStr(jsonObject.getString("package"));

        return vo;
    }

    @Override
    public PageResult page(int page, int pageSize, Integer status) {
        return null;
    }

    @Override
    public boolean cancel(Long id) {
        return false;
    }

    @Override
    public boolean repetition(Long id) {
        // 查询当前用户id
        Long userId = BaseContext.getCurrentId();

        // 根据订单id查询当前订单详情
        List<OrderDetail> orderDetailList = orderDetailMapper.selectList(new QueryWrapper<OrderDetail>().eq("order_id",id));

        // 将订单详情对象转换为购物车对象
        List<ShoppingCart> shoppingCartList = orderDetailList.stream().map(x -> {
            ShoppingCart shoppingCart = new ShoppingCart();

            // 将原订单详情里面的菜品信息重新复制到购物车对象中
            BeanUtils.copyProperties(x, shoppingCart, "id");
            shoppingCart.setUserId(userId);
            shoppingCart.setCreateTime(LocalDateTime.now());

            return shoppingCart;
        }).collect(Collectors.toList());

        // 将购物车对象批量添加到数据库
        shoppingCartClient.insertBatch(shoppingCartList);
        return true;
    }

    @Override
    public boolean reminder(Long id) {
        // 根据id查询订单
        Orders ordersDB = orderMapper.selectById(id);

        // 校验订单是否存在
        if (ordersDB == null) {
            throw new RuntimeException(MessageConstant.ORDER_STATUS_ERROR);
        }

        Map map = new HashMap();
        map.put("type",2); //1表示来单提醒 2表示客户催单
        map.put("orderId",id);
        map.put("content","订单号：" + ordersDB.getNumber());

        //通过websocket向客户端浏览器推送消息
//        webSocketServer.sendToAllClient(JSON.toJSONString(map));
        return true;
    }

    @Override
    public OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO) {

        //1. 处理各种业务异常（地址簿为空、购物车数据为空）
        AddressBook addressBook = addressClient.getAddressById(ordersSubmitDTO.getAddressBookId());
        if(addressBook == null){
            //抛出业务异常
            throw new RuntimeException(MessageConstant.ADDRESS_BOOK_IS_NULL);
        }

        //检查用户的收货地址是否超出配送范围
        //checkOutOfRange(addressBook.getCityName() + addressBook.getDistrictName() + addressBook.getDetail());

        //查询当前用户的购物车数据
        Long userId = BaseContext.getCurrentId();

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUserId(userId);
        List<ShoppingCart> shoppingCartList = shoppingCartClient.list(shoppingCart);

        if(shoppingCartList == null || shoppingCartList.size() == 0){
            //抛出业务异常
            throw new RuntimeException(MessageConstant.SHOPPING_CART_IS_NULL);
        }

        //2. 向订单表插入1条数据
        Orders orders = new Orders();
        BeanUtils.copyProperties(ordersSubmitDTO, orders);
        orders.setOrderTime(LocalDateTime.now());
        orders.setPayStatus(Orders.UN_PAID);
        orders.setStatus(Orders.PENDING_PAYMENT);
        orders.setNumber(String.valueOf(System.currentTimeMillis()));
        orders.setAddress(addressBook.getDetail());
        orders.setPhone(addressBook.getPhone());
        orders.setConsignee(addressBook.getConsignee());
        orders.setUserId(userId);

        orderMapper.insert(orders);

        List<OrderDetail> orderDetailList = new ArrayList<>();
        //3. 向订单明细表插入n条数据
        for (ShoppingCart cart : shoppingCartList) {
            OrderDetail orderDetail = new OrderDetail();//订单明细
            BeanUtils.copyProperties(cart, orderDetail);
            orderDetail.setOrderId(orders.getId());//设置当前订单明细关联的订单id
            orderDetailMapper.insert(orderDetail);
            orderDetailList.add(orderDetail);
        }

        //4. 清空当前用户的购物车数据
        shoppingCartClient.deleteByUserId(userId);

        //5. 封装VO返回结果
        OrderSubmitVO orderSubmitVO = OrderSubmitVO.builder()
                .id(orders.getId())
                .orderTime(orders.getOrderTime())
                .orderNumber(orders.getNumber())
                .orderAmount(orders.getAmount())
                .build();

        return orderSubmitVO;
    }

    @Override
    public PageResult pageQuery4User(int page, int pageSize, Integer status) {
        QueryWrapper<Orders> eq = new QueryWrapper<Orders>().eq("user_id", BaseContext.getCurrentId()).eq("status", status);


        Page<Orders> ordersPage = orderMapper.selectPage(new Page<>(page, pageSize), eq);

        List<OrderVO> list = new ArrayList();

        // 查询出订单明细，并封装入OrderVO进行响应
        if (ordersPage != null && ordersPage.getTotal() > 0) {
            for (Orders orders : ordersPage.getRecords()) {
                Long orderId = orders.getId();// 订单id

                // 查询订单明细
                List<OrderDetail> orderDetails = orderDetailMapper.selectList(new QueryWrapper<OrderDetail>().eq("order_id",orderId));

                OrderVO orderVO = new OrderVO();
                BeanUtils.copyProperties(orders, orderVO);
                orderVO.setOrderDetailList(orderDetails);

                list.add(orderVO);
            }
        }
        return new PageResult(ordersPage.getTotal(), list);
    }

    @Override
    public void userCancelById(Long id) {
        // 根据id查询订单
        Orders ordersDB = orderMapper.selectById(id);

        // 校验订单是否存在
        if (ordersDB == null) {
            throw new RuntimeException(MessageConstant.ORDER_NOT_FOUND);
        }

        //订单状态 1待付款 2待接单 3已接单 4派送中 5已完成 6已取消
        if (ordersDB.getStatus() > 2) {
            throw new RuntimeException(MessageConstant.ORDER_STATUS_ERROR);
        }

        Orders orders = new Orders();
        orders.setId(ordersDB.getId());

        // 订单处于待接单状态下取消，需要进行退款
        if (ordersDB.getStatus().equals(Orders.TO_BE_CONFIRMED)) {
            //调用微信支付退款接口
            try {
                weChatPayUtil.refund(
                        ordersDB.getNumber(), //商户订单号
                        ordersDB.getNumber(), //商户退款单号
                        new BigDecimal(0.01),//退款金额，单位 元
                        new BigDecimal(0.01));//原订单金额
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            //支付状态修改为 退款
            orders.setPayStatus(Orders.REFUND);
        }

        // 更新订单状态、取消原因、取消时间
        orders.setStatus(Orders.CANCELLED);
        orders.setCancelReason("用户取消");
        orders.setCancelTime(LocalDateTime.now());
        orderMapper.updateById(orders);
    }

    @Override
    public Double sumByMap(Map map) {
        QueryWrapper<Orders> eq = new QueryWrapper<Orders>().eq("status", map.get("status"));
        if (map.containsKey("begin")){
            eq.ge("order_time",map.get("begin"));
        }
        if (map.containsKey("end")){
            eq.le("order_time",map.get("end"));
        }
        List<Orders> orders = orderMapper.selectList(eq);
        System.out.println("***"+orders);
        double sum = 0.0;
        for (Orders o:orders){
            sum+=o.getAmount().doubleValue();
        }
        return sum;
    }

    @Override
    public Integer countByStatus(Map map) {
        QueryWrapper<Orders> eq = new QueryWrapper<Orders>();
        if (map.containsKey("start")){
            eq.ge("order_time",map.get("start"));
        }
        if (map.containsKey("end")){
            eq.le("order_time",map.get("end"));
        }
        if (map.get("status")!=null){
            eq.eq("status", map.get("status"));
        }
        return orderMapper.selectCount(eq);
    }

    @Override
    public List<GoodsSalesDTO> getSalesTop10(LocalDateTime begin, LocalDateTime end) {
        return orderMapper.getSalesTop10(begin,end);
    }


}