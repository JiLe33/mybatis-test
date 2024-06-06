package generator.Controller;



import generator.Config.R;

import generator.domain.Category;

import generator.domain.User;
import generator.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/category")
@RestController
public class Catagory {
    @Autowired
    private CategoryService categoryService;
    @GetMapping
    public R<List<Category>> getCatagory(){
        final List<Category> categoryList = categoryService.list();
        return R.success(categoryList);
    }
    @PutMapping
    public R<Category> update(@RequestBody Category category){
        categoryService.updateById(category);
        return R.success(category);
    }
    @DeleteMapping
    public R<String> delete(Integer categoryId){
        categoryService.removeById(categoryId);
        return R.success("删除成功");
    }
    @PostMapping
    public  R<String> save(@RequestBody Category category){
        categoryService.save(category);
        return R.success("添加成功");
    }
}
