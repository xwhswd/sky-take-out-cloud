package com.xwh.clients;

import com.xwh.pojo.vo.BusinessDataVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author xwh
 * @version 1.0
 * 2023/8/5
 */
@FeignClient(value = "server-workspace")
public interface WorkspaceClient {
    @PostMapping("/workspace/stastisticMessage")
    BusinessDataVO getSomedayBusinessData(@RequestBody Map<String,LocalDateTime> map);
}
