package generator;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@MapperScan("generator.mapper") // 扫描Mapper接口
public class MybatisTestApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(MybatisTestApplication.class, args);
        System.out.println("启动成功");
        System.out.println("web config:");
        String[] beanNamesForType = run.getBeanNamesForType(WebMvcConfigurer.class);
        for (String s : beanNamesForType) {
            System.out.println(s);
        }

    }

}
