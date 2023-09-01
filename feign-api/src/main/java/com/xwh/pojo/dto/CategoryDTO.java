package com.xwh.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xwh
 * @version 1.0
 * 2023/7/30
 */
@Data
public class CategoryDTO implements Serializable {

    //主键
    private Long id;

    //类型 1 菜品分类 2 套餐分类
    private Integer type;

    //分类名称
    private String name;

    //排序
    private Integer sort;

}