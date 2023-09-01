package com.xwh.task;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @author xwh
 * @version 1.0
 * 2023/8/3
 */
@Slf4j
public class MyTask {

    /**
     * 定时任务 每隔5秒触发一次
     */
    //@Scheduled(cron = "0/5 * * * * ?")
    public void executeTask(){
        log.info("定时任务开始执行：{}", new Date());
    }
}
