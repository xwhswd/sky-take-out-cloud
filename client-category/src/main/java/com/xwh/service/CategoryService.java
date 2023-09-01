package com.xwh.service;

import com.xwh.pojo.Category;
import com.xwh.pojo.dto.CategoryDTO;
import com.xwh.pojo.dto.CategoryPageQueryDTO;
import com.xwh.pojo.result.PageResult;

import java.util.List;

/**
 * @author xwh
 * @version 1.0
 * 2023/7/29
 */
public interface CategoryService {
    /**
     * 新增分类
     * @param categoryDTO
     * @return
     */
    boolean save(CategoryDTO categoryDTO);

    /**
     *分页查询分类
     * @param categoryPageQueryDTO
     * @return
     */
    PageResult page(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 删除分类
     * @param id
     * @return
     */
    boolean deleteById(Long id);

    /**
     * 更新分类
     * @param categoryDTO
     * @return
     */
    boolean update(CategoryDTO categoryDTO);

    /**
     * 启用禁用分类
     * @param status
     * @param id
     * @return
     */
    boolean startOrStop(Integer status,Long id);

    /**
     * 根据类型查询分类
     * @return
     */
    List<Category> listByCate(Integer type);

    /**
     * 工具id查询分类
     * @param id
     * @return
     */
    Category getByID(Long id);
}
