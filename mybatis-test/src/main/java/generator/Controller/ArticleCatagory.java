package generator.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import generator.Config.R;

import generator.domain.Article;
import generator.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController

@RequestMapping("/article")
public class ArticleCatagory {
    @Autowired
    private ArticleService articleService;

    @GetMapping
    public R<Page> list(final Integer pageNum,
                        final Integer pageSize,
                        @RequestParam(required = false) final Integer categoryId,
                        @RequestParam(required = false) final String state) {
       Page page = new Page<>(pageNum, pageSize);
       LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
      queryWrapper
          .eq(categoryId != null, Article::getCategoryId, categoryId)
          .eq(state != null, Article::getState, state);
       articleService.page(page, queryWrapper);
       return R.success(page);
    }



}
