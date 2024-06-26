package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.dto.DishDto;
import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.entity.DishFlavor;
import com.itheima.reggie.mapper.DishMapper;
import com.itheima.reggie.service.DishFlavorService;
import com.itheima.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper,Dish> implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;
    @Autowired
    private DishService dishService;


    /**
     * 新增菜品，同时保存对应的口味数据
     * @param dishDto
     */
    @Transactional
    public void saveWithFlavor(DishDto dishDto) {
        this.save(dishDto);
        Long id = dishDto.getId();
        List<DishFlavor> flavors = dishDto.getFlavors();
        for (DishFlavor item:flavors ) {
            item.setDishId(id);
        }
        dishFlavorService.saveBatch(flavors);
    }

    /**
     * 根据id查询菜品信息和对应的口味信息
     * @param id
     * @return
     */
    public DishDto getByIdWithFlavor(Long id) {
        Dish byId = this.getById(id);
        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(byId,dishDto);
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(DishFlavor::getDishId,id);
        dishDto.setFlavors(dishFlavorService.list(queryWrapper));
        return  dishDto;
    }

    @Override
    @Transactional
    public void updateWithFlavor(DishDto dishDto) {
      this.updateById(dishDto);
      LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper();
      queryWrapper.eq(DishFlavor::getDishId,dishDto.getId());
      dishFlavorService.remove(queryWrapper);
      List<DishFlavor> flavors = dishDto.getFlavors();
        for (DishFlavor item:flavors) {
            item.setDishId(dishDto.getId());
        }
      dishFlavorService.saveBatch(flavors);

    }

    @Override
    public void removeWithFlavor(List<Long> ids) {
        dishService.removeByIds(ids);
        dishFlavorService.removeByIds(ids);
    }

}
