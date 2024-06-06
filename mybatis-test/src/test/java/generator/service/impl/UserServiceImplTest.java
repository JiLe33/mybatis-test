package generator.service.impl;

import generator.domain.User;
import generator.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserServiceImplTest {
@Autowired
private UserService userService;
    @Test
    public void test(){
        userService.getById(1L);
    }
}