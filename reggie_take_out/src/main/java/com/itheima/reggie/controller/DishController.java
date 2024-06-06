package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.R;
import com.itheima.reggie.dto.DishDto;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.entity.DishFlavor;
import com.itheima.reggie.service.CategoryService;
import com.itheima.reggie.service.DishFlavorService;
import com.itheima.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/dish")
public class DishController {
    @Autowired
    DishService dishService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    DishFlavorService dishFlavorService;
    @GetMapping("/page")
    public R<Page> list(int page, int pageSize, String name){
        Page pageinfo=new Page(page,pageSize);
        Page DishDto=new Page();
        LambdaQueryWrapper<Dish> lambdaQueryWrapper=new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(name!=null,Dish::getName,name);
        lambdaQueryWrapper.orderByDesc(Dish::getUpdateTime);
        dishService.page(pageinfo,lambdaQueryWrapper);
        BeanUtils.copyProperties(pageinfo,DishDto,"records");
        List<Dish> records = pageinfo.getRecords();
        List<DishDto> list=new ArrayList<>();
        for (Dish dish : records) {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(dish,dishDto);
            Long categoryId = dish.getCategoryId();
            Category category=categoryService.getById(categoryId);
            if (category!=null){
                dishDto.setCategoryName(category.getName());
            }
            list.add(dishDto);
        }
        DishDto.setRecords(list);
        return R.success(DishDto);
    }
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto){
        log.info(dishDto.toString());
        dishService.saveWithFlavor(dishDto);
        return R.success("新增菜品成功");
    }
    @DeleteMapping()
    public R<String> delete(@RequestParam List<Long> ids){
    log.info(ids.toString());
    dishService.removeWithFlavor(ids);
    return R.success("删除菜品成功");
    }

    @PostMapping("status/{status}")
     public  R<String> updateStatus(@PathVariable Integer status,@RequestParam List<Long> ids){
        LambdaQueryWrapper<Dish> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(Dish::getId,ids);
        List<Dish> list=dishService.list(lambdaQueryWrapper);
        for (Dish dish : list) {
            dish.setStatus(status);
        }
        dishService.updateBatchById(list);
        return R.success("修改成功");
    }




    @GetMapping("{id}")
    public R<DishDto> get(@PathVariable Long id){
        log.info(id.toString());
        DishDto dishDto=dishService.getByIdWithFlavor(id);
        return R.success(dishDto);
    }


    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto){
        log.info(dishDto.toString());
        dishService.updateWithFlavor(dishDto);
        return R.success("修改菜品成功");
    }





    @GetMapping("/list")
    public R<List<DishDto>> list(Dish dish){
       Page<Dish> page=new Page<>();
       LambdaQueryWrapper<Dish>lambdaQueryWrapper=new LambdaQueryWrapper<>();
       lambdaQueryWrapper.eq(dish.getCategoryId()!=null,Dish::getCategoryId,dish.getCategoryId());
       lambdaQueryWrapper.eq(Dish::getStatus,1);
       lambdaQueryWrapper.orderByDesc(Dish::getUpdateTime);
       dishService.page(page,lambdaQueryWrapper);
        List<Dish> records = page.getRecords();
        List<DishDto> list=new ArrayList<>();
        for(Dish item:records){
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item,dishDto);
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            if(category!=null){
                String categoryName = category.getName();
               dishDto.setCategoryName(categoryName);
            }
            Long id = item.getId();
            LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper1=new LambdaQueryWrapper<>();
            lambdaQueryWrapper1.eq(DishFlavor::getDishId,id);
            dishDto.setFlavors(dishFlavorService.list(lambdaQueryWrapper1));
            list.add(dishDto);

        }
        return R.success(list);
    }
}
