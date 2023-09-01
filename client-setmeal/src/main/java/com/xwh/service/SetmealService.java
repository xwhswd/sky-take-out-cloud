package com.xwh.service;

import com.xwh.pojo.Setmeal;
import com.xwh.pojo.dto.SetmealDTO;
import com.xwh.pojo.dto.SetmealPageQueryDTO;
import com.xwh.pojo.result.PageResult;
import com.xwh.pojo.vo.DishItemVO;
import com.xwh.pojo.vo.SetmealVO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author xwh
 * @version 1.0
 * 2023/7/29
 */
public interface SetmealService {
    List<Setmeal> list(Long categoryId);

    boolean save(SetmealDTO setmealDTO);

    PageResult page(SetmealPageQueryDTO setmealPageQueryDTO);

    boolean delete(List<Long> ids);

    SetmealVO getById(Long id);

    Setmeal getSeatMealById(Long id);

    boolean update(SetmealDTO setmealDTO);

    boolean startOrStop(Integer status, Long id);

    List<Setmeal> list(Setmeal setmeal);

    List<DishItemVO> getDishItemById(Long id);

    void saveWithDish(SetmealDTO setmealDTO);

    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    void deleteBatch(List<Long> ids);

    SetmealVO getByIdWithDish(Long id);

    Integer countByStatus(Map map);
}
