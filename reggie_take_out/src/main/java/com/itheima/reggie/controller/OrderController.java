package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.BaseContext;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Orders;
import com.itheima.reggie.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String number, String beginTime, String endTime ){
        log.info("page = {},pageSize = {}" ,page,pageSize);
        //构造分页构造器
        Page pageInfo = new Page(page,pageSize);
        //构造条件构造器
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper();
        //添加排序条件
        queryWrapper.like(StringUtils.isNotEmpty(number),Orders::getNumber,number);
        queryWrapper.ge(StringUtils.isNotEmpty(beginTime),Orders::getOrderTime,beginTime);
        queryWrapper.le(StringUtils.isNotEmpty(endTime),Orders::getOrderTime,endTime);
        queryWrapper.orderByDesc(Orders::getOrderTime);
        //执行查询
        orderService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }
    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){
        log.info("订单数据：{}",orders);
        orderService.submit(orders);
        return R.success("下单成功");
    }


    @GetMapping("/userPage")
    public R<Page> userPage(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize){
        log.info("page = {},pageSize = {}" ,page,pageSize);
        LambdaQueryWrapper<Orders> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Orders::getUserId, BaseContext.getCurrentId());
        Page<Orders> pageInfo = new Page<>(page,pageSize);
        orderService.page(pageInfo,lambdaQueryWrapper);
        return R.success(pageInfo);

    }


}
