package com.xwh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xwh.mapper.CategoryMapper;
import com.xwh.pojo.Category;
import com.xwh.pojo.dto.CategoryDTO;
import com.xwh.pojo.dto.CategoryPageQueryDTO;
import com.xwh.pojo.result.PageResult;
import com.xwh.service.CategoryService;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xwh
 * @version 1.0
 * 2023/7/29
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public boolean save(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO,category);
        return categoryMapper.insert(category)>0;
    }

    @Override
    public PageResult page(CategoryPageQueryDTO categoryPageQueryDTO) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        if (categoryPageQueryDTO.getType()!=null){
            queryWrapper.eq(Category::getType,categoryPageQueryDTO.getType());
        }
        if (categoryPageQueryDTO.getName()!=null){
            queryWrapper.like(Category::getName,categoryPageQueryDTO.getName());
        }
        int count = categoryMapper.selectCount(queryWrapper);
        queryWrapper.last
                ("limit "+(categoryPageQueryDTO.getPage()-1)* categoryPageQueryDTO.getPageSize()+
                        ","+categoryPageQueryDTO.getPageSize());
        List<Category> categories = categoryMapper.selectList(queryWrapper);
        PageResult pageResult = new PageResult();
        pageResult.setTotal(count);
        pageResult.setRecords(categories);
        return pageResult;
    }

    @Override
    public boolean deleteById(Long id) {
        QueryWrapper<Category> categoryQueryWrapper = new QueryWrapper<>();
        categoryQueryWrapper.eq("id",id);
        return categoryMapper.delete(categoryQueryWrapper)>0;
    }

    @Override
    public boolean update(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO,category);
        return categoryMapper.updateById(category)>0;
    }

    @Override
    public boolean startOrStop(Integer status, Long id) {
        Category category = categoryMapper.selectById(id);
        category.setStatus(status);
        int i = categoryMapper.updateById(category);
        return i>0;
    }

    @Override
    public List<Category> listByCate(Integer type) {
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status",1);
        if (type!=null){
            queryWrapper.eq("type",type);
        }
        return categoryMapper.selectList(queryWrapper);
    }

    @Override
    public Category getByID(Long id) {
        return categoryMapper.selectById(id);
    }
}
