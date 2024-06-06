package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.Category;
import generator.service.CategoryService;
import generator.mapper.CategoryMapper;
import org.springframework.stereotype.Service;

/**
* @author 花见
* @description 针对表【category】的数据库操作Service实现
* @createDate 2024-05-27 22:02:25
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{

}




