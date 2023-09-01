package com.xwh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xwh.clients.CategoryClient;
import com.xwh.constants.MessageConstant;
import com.xwh.constants.StatusConstant;
import com.xwh.mapper.SetmealDishMapper;
import com.xwh.mapper.SetmealMapper;
import com.xwh.pojo.Setmeal;
import com.xwh.pojo.SetmealDish;
import com.xwh.pojo.dto.SetmealDTO;
import com.xwh.pojo.dto.SetmealPageQueryDTO;
import com.xwh.pojo.result.PageResult;
import com.xwh.pojo.vo.DishItemVO;
import com.xwh.pojo.vo.SetmealVO;
import com.xwh.service.SetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author xwh
 * @version 1.0
 * 2023/7/29
 */
@Service
public class SetmealServiceImpl implements SetmealService {
    @Resource
    private SetmealMapper setmealMapper;
    @Resource
    private SetmealDishMapper setmealDishMapper;
    @Resource
    private CategoryClient client;

    @Override
    public List<Setmeal> list(Long categoryId) {
        QueryWrapper<Setmeal> queryWrapper = new QueryWrapper();
        queryWrapper.eq("categoryid",categoryId);
        return setmealMapper.selectList(queryWrapper);
    }

    @Override
    public boolean save(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);
        return setmealMapper.insert(setmeal)>0;
    }

    @Override
    public PageResult page(SetmealPageQueryDTO setmealPageQueryDTO) {
        QueryWrapper<Setmeal> queryWrapper = new QueryWrapper();
        if (setmealPageQueryDTO.getName()!=null){
            queryWrapper.like("name",setmealPageQueryDTO.getName());
        }
        if (setmealPageQueryDTO.getCategoryId()!=0){
            queryWrapper.eq("categoryid",setmealPageQueryDTO.getCategoryId());
        }
        if (setmealPageQueryDTO.getStatus()!=null){
            queryWrapper.eq("status",setmealPageQueryDTO.getStatus());
        }
        PageResult pageResult = new PageResult();
        Integer count = setmealMapper.selectCount(queryWrapper);
        pageResult.setTotal(count);
        int start = (setmealPageQueryDTO.getPage()-1)* setmealPageQueryDTO.getPageSize();
        queryWrapper.last("limit "+start+","+setmealPageQueryDTO.getPageSize());
        List<Setmeal> setmeals = setmealMapper.selectList(queryWrapper);
        pageResult.setRecords(setmeals);
        return pageResult;
    }

    @Override
    public boolean delete(List<Long> ids) {
        int i = setmealMapper.deleteBatchIds(ids);
        return i>0;
    }

    @Override
    public SetmealVO getById(Long id) {
        Setmeal setmeal = setmealMapper.selectById(id);
        SetmealVO setmealVO = new SetmealVO();
        BeanUtils.copyProperties(setmeal,setmealVO);
        return setmealVO;
    }

    @Override
    public Setmeal getSeatMealById(Long id) {
        return setmealMapper.selectById(id);
    }

    @Override
    public boolean update(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);
        int i = setmealMapper.updateById(setmeal);
        return i>0;
    }

    @Override
    public boolean startOrStop(Integer status, Long id) {
        Setmeal setmeal = setmealMapper.selectById(id);
        setmeal.setStatus(status);
        int i = setmealMapper.updateById(setmeal);
        return i>0;
    }

    @Override
    public List<Setmeal> list(Setmeal setmeal) {
        List<Setmeal> setmeals = setmealMapper.selectList(new QueryWrapper<Setmeal>().eq("category_id", setmeal.getCategoryId()).eq("status", setmeal.getStatus()));
        return setmeals;
    }

    @Override
    public List<DishItemVO> getDishItemById(Long id) {
        return setmealMapper.getDishItemBySetmealId(id);
    }

    @Override
    public void saveWithDish(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);

        //向套餐表插入数据
        setmealMapper.insert(setmeal);

        //获取生成的套餐id
        Long setmealId = setmeal.getId();

        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        setmealDishes.forEach(setmealDish -> {
            setmealDish.setSetmealId(setmealId);
        });

        //保存套餐和菜品的关联关系
        setmealMapper.insertBatch(setmealDishes);
    }

    @Override
    public PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {
        int pageNum = setmealPageQueryDTO.getPage();
        int pageSize = setmealPageQueryDTO.getPageSize();
        QueryWrapper<Setmeal> queryWrapper = new QueryWrapper<>();
        if (setmealPageQueryDTO.getName()!=null){
            queryWrapper.like("name",setmealPageQueryDTO.getName());
        }
        if (setmealPageQueryDTO.getStatus()!=null){
            queryWrapper.eq("status",setmealPageQueryDTO.getStatus());
        }
        if (setmealPageQueryDTO.getCategoryId()!=null){
            queryWrapper.eq("category_id",setmealPageQueryDTO.getCategoryId());
        }
        Page<Setmeal> setmealPage = setmealMapper.selectPage(new Page<>(pageNum, pageSize), queryWrapper);
        List<SetmealVO> list = new ArrayList<>();
        for (Setmeal record : setmealPage.getRecords()) {
            SetmealVO setmealVO = new SetmealVO();
            setmealVO.setCategoryName(client.getById(record.getCategoryId()).getName());
            BeanUtils.copyProperties(record,setmealVO);
            list.add(setmealVO);
        }
        return new PageResult(setmealPage.getTotal(),list);
    }

    @Override
    public void deleteBatch(List<Long> ids) {
        ids.forEach(id -> {
            Setmeal setmeal = setmealMapper.selectById(id);
            if (StatusConstant.ENABLE == setmeal.getStatus()) {
                //起售中的套餐不能删除
                throw new RuntimeException(MessageConstant.SETMEAL_ON_SALE);
            }
        });

        ids.forEach(setmealId -> {
            //删除套餐表中的数据
            setmealMapper.deleteById(setmealId);
            //删除套餐菜品关系表中的数据
            setmealDishMapper.delete(new QueryWrapper<SetmealDish>().eq("setmeal_id",setmealId));
        });
    }

    @Override
    public SetmealVO getByIdWithDish(Long id) {
        return null;
    }

    @Override
    public Integer countByStatus(Map map) {
        return setmealMapper.selectCount(new QueryWrapper<Setmeal>().eq("status",map.get("status")));
    }
}
