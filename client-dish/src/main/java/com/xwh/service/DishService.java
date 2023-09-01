package com.xwh.service;

import com.xwh.pojo.Dish;
import com.xwh.pojo.dto.DishDTO;
import com.xwh.pojo.dto.DishPageQueryDTO;
import com.xwh.pojo.result.PageResult;
import com.xwh.pojo.vo.DishItemVO;
import com.xwh.pojo.vo.DishVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author xwh
 * @version 1.0
 * 2023/7/29
 */
public interface DishService {

    /**
     * 新增菜品
     * @param dishDTO
     * @return
     */
    boolean save(DishDTO dishDTO);

    /**
     * 分页查询菜品
     * @param dishPageQueryDTO
     * @return
     */
    PageResult page(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 批量删除菜品
     * @param ids
     * @return
     */
    boolean delete(List<Long> ids);

    /**
     * 根据id获取菜品
     * @param id
     * @return
     */
    DishVO getById(Long id);

    /**
     * 更新菜品
     * @param dishDTO
     * @return
     */
    boolean update(DishDTO dishDTO);

    /**
     * 启用禁用菜品
     * @param status
     * @param id
     * @return
     */
    boolean startOrStop(Integer status, Long id);

    /**
     * 根据类别显示菜品
     * @param categoryId
     * @return
     */
    List<Dish> list(Long categoryId);

    List<DishItemVO> dishList(Long id);

    Dish getByDishId(Long id);

    Integer countByStatus(Map map);
}
