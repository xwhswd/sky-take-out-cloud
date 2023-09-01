package com.xwh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xwh.clients.DishClient;
import com.xwh.clients.SetmealClient;
import com.xwh.context.BaseContext;
import com.xwh.mapper.CartMapper;
import com.xwh.pojo.Dish;
import com.xwh.pojo.Setmeal;
import com.xwh.pojo.ShoppingCart;
import com.xwh.pojo.dto.ShoppingCartDTO;
import com.xwh.service.CartService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author xwh
 * @version 1.0
 * 2023/7/29
 */
@Service
public class CartServiceImpl implements CartService {
    @Resource
    private CartMapper cartMapper;
    @Resource
    private DishClient dishClient;
    @Resource
    private SetmealClient setmealClient;

    @Override
    public boolean add(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO,shoppingCart);
        shoppingCart.setUserId(BaseContext.getCurrentId());

        List<ShoppingCart> list = list();
        //如果已经存在了，只需要将数量加一
        if(list != null && list.size() > 0){
            ShoppingCart cart = list.get(0);
            cart.setNumber(cart.getNumber() + 1);//update shopping_cart set number = ? where id = ?
            return cartMapper.updateById(cart)>0;
        }else {
            //如果不存在，需要插入一条购物车数据
            //判断本次添加到购物车的是菜品还是套餐
            Long dishId = shoppingCartDTO.getDishId();
            if(dishId != null){
                //本次添加到购物车的是菜品
                Dish dish = dishClient.getDishById(dishId);
                shoppingCart.setName(dish.getName());
                shoppingCart.setImage(dish.getImage());
                shoppingCart.setAmount(dish.getPrice());
            }else{
                //本次添加到购物车的是套餐
                Long setmealId = shoppingCartDTO.getSetmealId();
                Setmeal setmeal = setmealClient.getSetMealById(setmealId);
                shoppingCart.setName(setmeal.getName());
                shoppingCart.setImage(setmeal.getImage());
                shoppingCart.setAmount(setmeal.getPrice());
            }
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            return cartMapper.insert(shoppingCart)>0;
        }
    }

    @Override
    public List<ShoppingCart> list() {
        Long currentId = BaseContext.getCurrentId();
        QueryWrapper<ShoppingCart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",currentId);
        return cartMapper.selectList(queryWrapper);
    }

    @Override
    public boolean cleanShoppingCart() {
        Long currentId = BaseContext.getCurrentId();
        QueryWrapper<ShoppingCart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",currentId);
        return cartMapper.delete(queryWrapper)>0;
    }

    @Override
    public boolean subShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        Integer integer;
        QueryWrapper<ShoppingCart> queryWrapper = new QueryWrapper<>();
        if (shoppingCartDTO.getDishId()!=null){
            queryWrapper.eq("dish_id",shoppingCartDTO.getDishId());
            queryWrapper.eq("user_id",BaseContext.getCurrentId());
            integer = cartMapper.selectCount(queryWrapper);

        }else {
            queryWrapper.eq("setmeal_id", shoppingCartDTO.getSetmealId());
            queryWrapper.eq("user_id",BaseContext.getCurrentId());
        }
        ShoppingCart shoppingCart = cartMapper.selectOne(queryWrapper);
        if (shoppingCart.getNumber()>1){
            shoppingCart.setNumber(shoppingCart.getNumber()-1);
            shoppingCart.setAmount(shoppingCart.getAmount().divide(BigDecimal.valueOf(shoppingCart.getNumber())).multiply(BigDecimal.valueOf(shoppingCart.getNumber()-1)));
            return cartMapper.updateById(shoppingCart)>0;
        }else {
            return cartMapper.deleteById(shoppingCart.getId())>0;
        }
    }

    @Override
    public void deleteById(Long id){
        cartMapper.delete(new QueryWrapper<ShoppingCart>().eq("user_id",id));
    }

    @Override
    public void insertBatch(List<ShoppingCart> shoppingCartList) {
        for (ShoppingCart shoppingCart : shoppingCartList) {
            cartMapper.insert(shoppingCart);
        }
    }

}
