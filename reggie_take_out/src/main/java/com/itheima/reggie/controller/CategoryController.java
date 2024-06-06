package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize){
        Page<Category> pageInfo = new Page(page,pageSize);
        LambdaQueryWrapper<Category> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(Category::getSort,Category::getUpdateTime);
        categoryService.page(pageInfo,lambdaQueryWrapper);
        return R.success(pageInfo);
    }
    @PostMapping
    public R<String> save(@RequestBody Category category){
        categoryService.save(category);
        return R.success("新增分类成功");
    }
    @DeleteMapping
    public R<String> delete( Long id){
        categoryService.remove(id);
        return R.success("分类信息删除成功");
    }
    @PutMapping
    public R<String> update(Category category){
        categoryService.updateById(category);
        return R.success("分类信息修改成功");
    }
    @GetMapping("list")
    public R<List<Category>> list(Category category){
    LambdaQueryWrapper<Category>lambdaQueryWrapper=new LambdaQueryWrapper<>();
    lambdaQueryWrapper.eq(category.getType()!=null,Category::getType,category.getType());
    lambdaQueryWrapper.orderByDesc(Category::getSort).orderByAsc(Category::getUpdateTime);
    List<Category> list = categoryService.list(lambdaQueryWrapper);
    return R.success(list);
    }
}
