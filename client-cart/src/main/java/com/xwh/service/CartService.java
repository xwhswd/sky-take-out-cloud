package com.xwh.service;

import com.xwh.pojo.ShoppingCart;
import com.xwh.pojo.dto.ShoppingCartDTO;

import java.util.List;

/**
 * @author xwh
 * @version 1.0
 * 2023/7/29
 */
public interface CartService {
    /**
     * 购物车新增
     * @param shoppingCartDTO
     * @return
     */
    boolean add(ShoppingCartDTO shoppingCartDTO);

    /**
     * 展示购物车
     * @return
     */
    List<ShoppingCart> list();

    /**
     * 清空购物车
     * @return
     */
    boolean cleanShoppingCart();

    /**
     * 购物车中删除某商品
     * @param shoppingCartDTO
     * @return
     */
    boolean subShoppingCart(ShoppingCartDTO shoppingCartDTO);

    void deleteById(Long id);

    void insertBatch(List<ShoppingCart> shoppingCartList);
}
