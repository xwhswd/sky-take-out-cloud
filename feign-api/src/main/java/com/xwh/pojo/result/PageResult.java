package com.xwh.pojo.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
/**
 * @author xwh
 * @version 1.0
 * 2023/7/28
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult implements Serializable {
    private long total;
    private List records;
}
