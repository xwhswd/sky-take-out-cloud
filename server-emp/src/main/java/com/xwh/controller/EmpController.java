package com.xwh.controller;

import com.xwh.config.JwtProperties;
import com.xwh.constants.JwtClaimsConstant;
import com.xwh.pojo.Employee;
import com.xwh.pojo.dto.EmployeeDTO;
import com.xwh.pojo.dto.EmployeeLoginDTO;
import com.xwh.pojo.dto.EmployeePageQueryDTO;
import com.xwh.pojo.result.PageResult;
import com.xwh.pojo.result.Result;
import com.xwh.pojo.vo.EmployeeLoginVO;
import com.xwh.service.EmpService;
import com.xwh.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xwh
 * @version 1.0
 * 2023/7/28
 */
@RestController
@RequestMapping("/employee")
@Api("员工信息接口")
@Slf4j
public class EmpController {
    @Resource
    private EmpService empService;
    @Autowired
    private RedisTemplate redisTemplate;

    @Resource
    private JwtProperties jwtProperties;

    @PostMapping("/login")
    @ApiOperation("员工登录")
    public Result login(@RequestBody EmployeeLoginDTO employeeLoginDTO){
        log.info("----员工登录:"+employeeLoginDTO+"----");
        System.out.println("12345");
        Result result = new Result<>();
        Employee employee = empService.login(employeeLoginDTO);
        String token = JwtUtil.getToken(employeeLoginDTO.getUsername());
        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName (employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();
        log.info("存入redis");
        redisTemplate.opsForValue().set("jwt_"+token,employee.getId());
        return Result.success(employeeLoginVO);
    }

//    @PostMapping("/login")
//    @ApiOperation(value = "员工登录")
//    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
//        log.info("----员工登录：", employeeLoginDTO+"----");
//        Employee employee = empService.login(employeeLoginDTO);
//        //登录成功后，生成jwt令牌
//        Map<String, Object> claims = new HashMap<>();
//        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
//        String token = JwtUtil.createJWT(
//                jwtProperties.getAdminSecretKey(),
//                jwtProperties.getAdminTtl(),
//                claims);
//
//        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
//                .id(employee.getId())
//                .userName(employee.getUsername())
//                .name(employee.getName())
//                .token(token)
//                .build();
//
//        return Result.success(employeeLoginVO);
//    }


    @PostMapping("/logout")
    @ApiOperation("员工退出")
    public Result<String> logout() {
        log.info("----员工登出:----");
        return Result.success();
    }

    @PostMapping
    @ApiOperation("新增员工")
    public Result save(@RequestBody EmployeeDTO employeeDTO){
        log.info("----新增员工:"+employeeDTO+"----");
        empService.save(employeeDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("员工分页查询")
    public Result<PageResult> page(EmployeePageQueryDTO employeePageQueryDTO){
        log.info("----员工分类查询:"+employeePageQueryDTO+"----");
        PageResult pageResult = empService.page(employeePageQueryDTO);
        return Result.success(pageResult);
    }

    @PostMapping("/status/{status}")
    @ApiOperation("启用禁用员工账号")
    public Result startOrStop(@PathVariable Integer status,Long id){
        log.info("----启用禁用员工账号"+"----");
        boolean b = empService.startOrStop(status, id);
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查询员工信息")
    public Result<Employee> getById(@PathVariable Long id){
        log.info("----根据id查询员工信息:"+id+"----");
        Employee employee = empService.getById(id);
        return Result.success(employee);
    }

    @PutMapping
    @ApiOperation("编辑员工信息")
    public Result update(@RequestBody EmployeeDTO employeeDTO){
        log.info("----编辑员工信息:"+employeeDTO+"----");
        empService.update(employeeDTO);
        return Result.success();
    }
}
