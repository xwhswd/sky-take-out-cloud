package com.xwh.exception;

/**
 * @author xwh
 * @version 1.0
 * 2023/7/28
 */
public enum SkyException {

    LOGIN_FAIL("","");

    public String  code;
    public String  msg;

    private SkyException(String code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
