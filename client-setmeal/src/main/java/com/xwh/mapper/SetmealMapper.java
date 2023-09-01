package com.xwh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xwh.pojo.Setmeal;
import com.xwh.pojo.SetmealDish;
import com.xwh.pojo.vo.DishItemVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author xwh
 * @version 1.0
 * 2023/7/29
 */
@Mapper
public interface SetmealMapper extends BaseMapper<Setmeal> {
    @Insert("<script> insert into setmeal_dish (setmeal_id,dish_id,name,price,copies)" +
            " values" +
            " <foreach collection='setmealDishes' item='sd' separator=','>" +
            " (#{sd.setmealId},#{sd.dishId},#{sd.name},#{sd.price},#{sd.copies})" +
            " </foreach> </script>")
    void insertBatch(List<SetmealDish> setmealDishes);

    @Select("select sd.name, sd.copies, d.image, d.description " +
            "from setmeal_dish sd left join dish d on sd.dish_id = d.id " +
            "where sd.setmeal_id = #{setmealId}")
    List<DishItemVO> getDishItemBySetmealId(Long setmealId);
}
