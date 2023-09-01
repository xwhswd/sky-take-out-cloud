package com.xwh.controller;

import com.xwh.context.BaseContext;
import com.xwh.pojo.ShoppingCart;
import com.xwh.pojo.dto.ShoppingCartDTO;
import com.xwh.pojo.result.Result;
import com.xwh.service.CartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author xwh
 * @version 1.0
 * 2023/7/29
 */
@RestController
@RequestMapping("/shoppingCart")
@Api("购物车操作接口")
@Slf4j
public class ShoppingCartController {
    @Resource
    private CartService cartService;
    @Resource
    private RedisTemplate redisTemplate;
    @ModelAttribute
    public void setBaseContext(HttpServletRequest request){
        BaseContext.setCurrentId((Long) redisTemplate.opsForValue().get("jwt_"+request.getHeader("token")));
    }

    @PostMapping("/add")
    @ApiOperation("添加购物车")
    public Result add(@RequestBody ShoppingCartDTO shoppingCartDTO, HttpServletRequest request){
        log.info("----正在新增用户购物车信息"+ BaseContext.getCurrentId()+":"+shoppingCartDTO+"----");
        String token = request.getHeader("token");
        boolean add = cartService.add(shoppingCartDTO);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("查看购物车信息")
    public Result<List<ShoppingCart>> list(HttpServletRequest request){
        log.info("----正在查看用户购物车信息"+ BaseContext.getCurrentId()+"----");
        List<ShoppingCart> list = cartService.list();
        return Result.success(list);
    }

    @DeleteMapping("/clean")
    @ApiOperation("清空购物车")
    public Result clean(HttpServletRequest request){
        log.info("----正在清空用户购物车信息"+ BaseContext.getCurrentId()+"----");
        cartService.cleanShoppingCart();
        return Result.success();
    }

    @PostMapping("/sub")
    @ApiOperation("删除购物车中某个商品")
    public Result sub(@RequestBody ShoppingCartDTO shoppingCartDTO,HttpServletRequest request){
        log.info("---删除用户购物车商品信息"+ BaseContext.getCurrentId()+":"+shoppingCartDTO+"----");
        cartService.subShoppingCart(shoppingCartDTO);
        return Result.success();
    }

    @PostMapping("/listAll")
    List<ShoppingCart> list(@RequestBody ShoppingCart shoppingCart){
        return cartService.list();
    }

    @PostMapping("/deleteById")
    void deleteByUserId(@RequestBody Long userId){
        cartService.deleteById(userId);
    }

    @PostMapping("/insertBatch")
    void insertBatch(@RequestBody List<ShoppingCart> shoppingCartList){
        cartService.insertBatch(shoppingCartList);
    }
}
