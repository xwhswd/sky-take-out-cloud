package com.xwh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xwh.pojo.Orders;
import com.xwh.pojo.dto.GoodsSalesDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.checkerframework.common.aliasing.qual.MaybeAliased;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author xwh
 * @version 1.0
 * 2023/7/29
 */
@Mapper
public interface OrderMapper extends BaseMapper<Orders> {
    @Select(" <script>       select od.name, sum(od.number) number" +
            "        from order_detail od,orders o" +
            "        where od.order_id = o.id and o.status = 5" +
            "        <if test='begin != null'>" +
            "            and o.order_time &gt; #{begin}" +
            "        </if>" +
            "        <if test='end != null'>" +
            "            and o.order_time &lt; #{end}" +
            "        </if>" +
            "        group by od.name" +
            "        order by number desc" +
            "        limit 0,10  </script>")
    List<GoodsSalesDTO> getSalesTop10(LocalDateTime begin, LocalDateTime end);
}
