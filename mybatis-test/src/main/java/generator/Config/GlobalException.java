package generator.Config;

import generator.Exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalException {

        /**
         * 捕获业务异常
         * @param ex
         * @return
         */@ExceptionHandler
       public R exceptionHandler(UserException ex){
            log.error("异常信息：{}", ex.getMessage());
            return R.error(ex.getMessage());
        }
}
