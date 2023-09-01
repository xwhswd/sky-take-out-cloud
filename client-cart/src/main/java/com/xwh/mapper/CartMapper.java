package com.xwh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xwh.pojo.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author xwh
 * @version 1.0
 * 2023/7/29
 */
@Mapper
public interface CartMapper extends BaseMapper<ShoppingCart> {
}
