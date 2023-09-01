package com.xwh.clients;

import com.xwh.pojo.dto.GoodsSalesDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author xwh
 * @version 1.0
 * 2023/8/4
 */
@FeignClient("client-order")
public interface OrderClient {
    @PostMapping("/order/sumByMap")
    Double sumByMap(@RequestBody Map map);

    @PostMapping("/order/countByMap")
    Integer countByMap(@RequestBody Map map);

    @PostMapping("/order/getSalesTop10")
    List<GoodsSalesDTO> getSalesTop10(@RequestBody Map<String,LocalDateTime> map);
}
