package com.xwh.clients;

import com.xwh.pojo.ShoppingCart;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author xwh
 * @version 1.0
 * 2023/8/9
 */
@FeignClient("client-cart")
public interface ShoppingCartClient {
    @PostMapping("/shoppingcart/listAll")
    List<ShoppingCart> list(@RequestBody ShoppingCart shoppingCart);

    @PostMapping("/shoppingcart/deleteById")
    void deleteByUserId(@RequestBody Long userId);

    @PostMapping("/shoppingcart/insertBatch")
    void insertBatch(@RequestBody List<ShoppingCart> shoppingCartList);
}
