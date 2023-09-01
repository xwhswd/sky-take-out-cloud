package com.xwh.service;

import com.xwh.pojo.Employee;
import com.xwh.pojo.dto.EmployeeDTO;
import com.xwh.pojo.dto.EmployeeLoginDTO;
import com.xwh.pojo.dto.EmployeePageQueryDTO;
import com.xwh.pojo.result.PageResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author xwh
 * @version 1.0
 * 2023/7/28
 */
public interface EmpService {
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    boolean save(EmployeeDTO employeeDTO);

    PageResult page(EmployeePageQueryDTO employeePageQueryDTO);

    boolean startOrStop(Integer status, Long id);

    Employee getById(Long id);

    boolean update(EmployeeDTO employeeDTO);
}
