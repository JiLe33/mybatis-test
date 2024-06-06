package generator.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.Config.ThreadLocalUtil;
import generator.domain.Article;

import generator.service.ArticleService;
import generator.mapper.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
* @author 花见
* @description 针对表【article】的数据库操作Service实现
* @createDate 2024-05-27 22:02:25
*/
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
    implements ArticleService{

}




