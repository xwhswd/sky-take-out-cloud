package com.xwh.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xwh
 * @version 1.0
 * 2023/7/30
 */
@Data
public class ShoppingCartDTO implements Serializable {

    private Long dishId;
    private Long setmealId;
    private String dishFlavor;

}
