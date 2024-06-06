package generator.Config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import generator.domain.User;
import generator.service.UserService;
import org.apache.ibatis.reflection.MetaObject;

import java.sql.Date;
import java.time.LocalDateTime;

public class MybatisAop implements MetaObjectHandler {
    private UserService userService;

    @Override
    public void insertFill(MetaObject metaObject) {
        Long  id = ThreadLocalUtil.get();
        User user = userService.getById(id);
        metaObject.setValue("create_user", user.getUsername());
       metaObject.setValue("create_time", LocalDateTime.now());

    }

    @Override
    public void updateFill(MetaObject metaObject) {


        metaObject.setValue("update_time", LocalDateTime.now());
    }
}
