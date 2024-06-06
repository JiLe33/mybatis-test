package generator.Interceptor;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import generator.Config.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

import static generator.util.JwtUtil.getUserIdFromToken;

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    public static final String AUTHORIZATION_HEADER = "Authorization";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) {
        final String token = request.getHeader(AUTHORIZATION_HEADER);

        try {
            final ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            final String redisToken = operations.get(token);
            if (redisToken == null) { // Token has expired
                throw new RuntimeException("过期或未登录");
            }
        long id= getUserIdFromToken(token);
            ThreadLocalUtil.set(id);
            return true;
        } catch (Exception e) {
            response.setStatus(401);
            return false;
        }
    }

    @Override
    public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final Exception ex) throws Exception {
        ThreadLocalUtil.remove();
    }
}
