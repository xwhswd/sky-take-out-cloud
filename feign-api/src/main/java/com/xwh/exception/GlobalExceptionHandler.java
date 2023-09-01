package com.xwh.exception;

import com.xwh.pojo.result.Result;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author xwh
 * @version 1.0
 * 2023/7/28
 */
@RestControllerAdvice
public class GlobalExceptionHandler {


    public Result handle(){
        Result<Object> objectResult = new Result<>();
        return objectResult;
    }
}

