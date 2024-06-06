package generator.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import generator.Config.R;
import generator.Config.ThreadLocalUtil;
import generator.domain.Category;
import generator.domain.User;
import generator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Random;

import static generator.util.JwtUtil.generateToken;
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @PostMapping("/login")
    public R<String> test(@RequestParam("username") String username, @RequestParam("password") String password) {
        LambdaQueryWrapper<User> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername,username);
        lambdaQueryWrapper.eq(User::getPassword,password);
        User student=userService.getOne(lambdaQueryWrapper);
        if (student!=null){

            String token=generateToken(Long.valueOf(student.getId()));

            final ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            // If key is token, and when we validate, and the token doesn't exist, then it has expired.
            operations.set(token, token, Duration.ofHours(12));
            return R.success(token);
        }
        return R.error("登录失败");
    }
    @GetMapping("/userInfo")
    public R<User> userInfo() {

        long  id = ThreadLocalUtil.get();
        User user=userService.getById(id);
        if (user!=null){
            return R.success(user);
        }
        return R.error("用户不存在");
    }
   @PostMapping("/register")
    public R<String> register(User user){//如果加上@RequestBody，那么前端传过来的参数必须加@RequestBody
       User user1 = new User();
       user1.setUsername(user.getUsername());
       user1.setPassword(user.getPassword());
       userService.save(user1);
       return R.success("注册成功");
   }

}
