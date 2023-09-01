package com.xwh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xwh.context.BaseContext;
import com.xwh.mapper.EmpMapper;
import com.xwh.pojo.Employee;
import com.xwh.pojo.dto.EmployeeDTO;
import com.xwh.pojo.dto.EmployeeLoginDTO;
import com.xwh.pojo.dto.EmployeePageQueryDTO;
import com.xwh.pojo.result.PageResult;
import com.xwh.service.EmpService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xwh
 * @version 1.0
 * 2023/7/28
 */
@Service
public class EmpServiceImpl implements EmpService {
    @Resource
    private EmpMapper empMapper;

    @Override
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        Employee employee = new Employee();
        QueryWrapper<Employee> wrapper = new QueryWrapper<>();
        wrapper.eq("username",employeeLoginDTO.getUsername());
        wrapper.eq("password",employeeLoginDTO.getPassword());
        employee = empMapper.selectOne(wrapper);
        BaseContext.setCurrentId(employee.getId());
        return employee;
    }

    @Override
    public boolean save(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO,employee);
        employee.setPassword("123456");
        int insert = empMapper.insert(employee);
        return insert>0;
    }

    @Override
    public PageResult page(EmployeePageQueryDTO employeePageQueryDTO) {
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
        if (employeePageQueryDTO.getName()!=null){
            queryWrapper.like("name",employeePageQueryDTO.getName());
        }
        int count = empMapper.selectCount(queryWrapper);
        queryWrapper.last("limit "+(employeePageQueryDTO.getPage()-1)* employeePageQueryDTO.getPageSize()+
                ","+employeePageQueryDTO.getPageSize());
        List<Employee> employees = empMapper.selectList(queryWrapper);
        PageResult pageResult = new PageResult();
        pageResult.setTotal(count);
        pageResult.setRecords(employees);
        return pageResult;
    }

    @Override
    public boolean startOrStop(Integer status, Long id) {
        Employee employee = empMapper.selectById(id);
        employee.setStatus(status);
        int i = empMapper.updateById(employee);
        return i>0;
    }

    @Override
    public Employee getById(Long id) {
        return empMapper.selectById(id);
    }

    @Override
    public boolean update(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",employeeDTO.getId());
        BeanUtils.copyProperties(employeeDTO,employee);
        return empMapper.update(employee,queryWrapper)>0;
    }
}
