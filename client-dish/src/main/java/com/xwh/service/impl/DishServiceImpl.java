package com.xwh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xwh.clients.CategoryClient;
import com.xwh.mapper.DishFlavorMapper;
import com.xwh.mapper.DishMapper;
import com.xwh.pojo.Category;
import com.xwh.pojo.Dish;
import com.xwh.pojo.DishFlavor;
import com.xwh.pojo.dto.DishDTO;
import com.xwh.pojo.dto.DishPageQueryDTO;
import com.xwh.pojo.result.PageResult;
import com.xwh.pojo.vo.DishItemVO;
import com.xwh.pojo.vo.DishVO;
import com.xwh.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author xwh
 * @version 1.0
 * 2023/7/29
 */
@Service
public class DishServiceImpl implements DishService {
    @Resource
    private DishMapper dishMapper;

    @Resource
    private DishFlavorMapper dishFlavorMapper;

    @Resource
    private CategoryClient client;

    @Override
    public boolean save(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        return dishMapper.insert(dish)>0;
    }

    @Override
    public PageResult page(DishPageQueryDTO dishPageQueryDTO) {
        QueryWrapper<Dish> queryWrapper = new QueryWrapper<>();
        if (dishPageQueryDTO.getName()!=null){
            queryWrapper.like("name",dishPageQueryDTO.getName());
        }
        if (dishPageQueryDTO.getStatus()!=null){
            queryWrapper.like("status",dishPageQueryDTO.getStatus());
        }
        if (dishPageQueryDTO.getCategoryId()!=null){
            queryWrapper.like("category_id",dishPageQueryDTO.getCategoryId());
        }
        int count = dishMapper.selectCount(queryWrapper);
        queryWrapper.last("limit "+(dishPageQueryDTO.getPage()-1)*dishPageQueryDTO.getPageSize()+","+
                dishPageQueryDTO.getPageSize());
        List<Dish> dishes = dishMapper.selectList(queryWrapper);
        ArrayList<DishVO> dishVOS = new ArrayList<>(dishes.size());
        for (int i = 0; i < dishes.size(); i++) {
            DishVO dishVO = new DishVO();
            Category byId = client.getById(dishes.get(i).getCategoryId());
            BeanUtils.copyProperties(dishes.get(i),dishVO);
            QueryWrapper<DishFlavor> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("dish_id",dishes.get(i).getId());
            List<DishFlavor> dishFlavors = dishFlavorMapper.selectList(queryWrapper1);
            dishVO.setFlavors(dishFlavors);
            dishVO.setCategoryName(byId.getName());
            dishVOS.add(dishVO);
        }
        System.out.println(dishVOS);
        PageResult pageResult = new PageResult();
        pageResult.setTotal(count);
        pageResult.setRecords(dishVOS);
        return pageResult;
    }

    @Override
    public boolean delete(List<Long> ids) {
        int i = dishMapper.deleteBatchIds(ids);
        return i>0;
    }

    @Override
    public DishVO getById(Long id) {
        Dish dish = dishMapper.selectById(id);
        Long categoryId = dish.getCategoryId();
        Category byId = client.getById(categoryId);
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish,dishVO);
        dishVO.setCategoryName(byId.getName());
        return dishVO;
    }

    @Override
    public boolean update(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        return dishMapper.updateById(dish)>0;
    }

    @Override
    public boolean startOrStop(Integer status, Long id) {
        Dish dish = dishMapper.selectById(id);
        dish.setStatus(status);
        return dishMapper.updateById(dish)>0;
    }

    @Override
    public List<Dish> list(Long categoryId) {
        QueryWrapper<Dish> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category_id",categoryId);
        return dishMapper.selectList(queryWrapper);
    }

    @Override
    public List<DishItemVO> dishList(Long id) {
        return dishMapper.getDishItemBySetmealId(id);
    }

    @Override
    public Dish getByDishId(Long id) {
        return dishMapper.selectById(id);
    }

    @Override
    public Integer countByStatus(Map map) {
        return dishMapper.selectCount(new QueryWrapper<Dish>().eq("status",map.get("status")));
    }
}
