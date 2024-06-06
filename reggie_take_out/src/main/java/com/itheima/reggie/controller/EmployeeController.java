package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Employee;
import com.itheima.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("employee")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;
@PostMapping("/login")
public R<Employee> login(@RequestBody Employee employee, HttpServletRequest request){
    log.info("user:{}",employee);
    LambdaQueryWrapper<Employee> lambdaQueryWrapper=new LambdaQueryWrapper<>();
    lambdaQueryWrapper.eq(Employee::getUsername,employee.getUsername());
    Employee emp=employeeService.getOne(lambdaQueryWrapper);
    if (emp==null){
        return R.error("用户不存在");
    }
    if (!emp.getPassword().equals(employee.getPassword())){
        return R.error("密码错误");
    }
    if (emp.getStatus()==0){
        return R.error("账号已禁用");
    }
    request.getSession().setAttribute("employee",emp.getId());
    return R.success(emp);
}
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
    request.getSession().removeAttribute("employee");
    return R.success("退出成功");
    }
    @PostMapping
    public R<String> add(@RequestBody Employee employee){
    log.info(employee.toString());
          employee.setPassword("123456");
        employeeService.save(employee);
    return R.success("添加成功");
    }
    @GetMapping("/page")
    public R<Page> list(int page,int pageSize,String name){
        Page pageInfo = new Page(page, pageSize);
        LambdaQueryWrapper<Employee> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(StringUtils.isNotEmpty(name),Employee::getName,name);
        lambdaQueryWrapper.orderByDesc(Employee::getUpdateTime);
        employeeService.page(pageInfo,lambdaQueryWrapper);
        return R.success(pageInfo);
    }



    @PutMapping
    public R<String> update(@RequestBody Employee employee){
    employeeService.updateById(employee);
    return R.success("更改成功");
    }



    @GetMapping("/{id}")
    public R<Employee> employee(@PathVariable long id){
    LambdaQueryWrapper<Employee>lambdaQueryWrapper=new LambdaQueryWrapper<>();
    lambdaQueryWrapper.eq(Employee::getId,id);
    Employee employee=employeeService.getOne(lambdaQueryWrapper);
    if (employee==null){
        return R.error("没有查询到员工信息");
    }
    return R.success(employee);
    }
}
