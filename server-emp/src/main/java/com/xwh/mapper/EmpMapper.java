package com.xwh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xwh.pojo.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author xwh
 * @version 1.0
 * 2023/7/28
 */
@Mapper
public interface EmpMapper extends BaseMapper<Employee> {
}
