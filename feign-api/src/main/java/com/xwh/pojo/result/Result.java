package com.xwh.pojo.result;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xwh
 * @version 1.0
 * 2023/7/28
 */
@Data
public class Result<T> implements Serializable {
    private Integer code=1;
    private String msg;
    private T data;


    public static <T> Result<T> success() {
        Result<T> result = new Result<T>();
        result.code = 1;
        return result;
    }

    public static <T> Result<T> success(T object) {
        Result<T> result = new Result<T>();
        result.data = object;
        result.code = 1;
        return result;
    }

    public static <T> Result<T> error(String msg) {
        Result result = new Result();
        result.msg = msg;
        result.code = 0;
        return result;
    }

}
