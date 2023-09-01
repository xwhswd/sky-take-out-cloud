package com.xwh.task.powerJob;

import org.springframework.stereotype.Component;
import tech.powerjob.common.utils.HttpUtils;
import tech.powerjob.worker.core.processor.ProcessResult;
import tech.powerjob.worker.core.processor.TaskContext;
import tech.powerjob.worker.core.processor.sdk.BasicProcessor;

import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author xwh
 * @version 1.0
 * 2023/8/24
 */
@Component
public class MyProcessor implements BasicProcessor {
    @Override
    public ProcessResult process(TaskContext taskContext) throws Exception {
        System.out.println("test");
        return new ProcessResult(true,taskContext+":"+"true");
    }
}
