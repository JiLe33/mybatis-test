package generator.Config;

import generator.Interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
//@EnableWebMvc
@Configuration
public class WebConfig implements WebMvcConfigurer {
    private static final List<String> BYPASS_PATHS = List.of(
            "/user/login",
            "/user/register");

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {

        registry.addInterceptor(loginInterceptor)
                .excludePathPatterns("/user/register")
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/error")

              ;
    }
}
