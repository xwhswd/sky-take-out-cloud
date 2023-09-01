package com.xwh.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xwh
 * @version 1.0
 * 2023/7/28
 */
@Data
public class EmployeeLoginDTO implements Serializable {

    private String username;

    private String password;

}

