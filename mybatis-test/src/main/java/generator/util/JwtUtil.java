package generator.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import java.util.Date;

public class JwtUtil {
    private static final String SECRET_KEY = "your_secret_key_here";

    public static String generateToken(Long id) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + 3600 * 1000); // 过期时间为1小时
        return JWT.create()
                .withClaim("id", id)
                .withIssuedAt(now)//签发时间
                .withExpiresAt(expiration)//过期时间
                .sign(Algorithm.HMAC256(SECRET_KEY));//使用 HMAC256 算法和提供的密钥 SECRET_KEY 对 JWT 进行签名。
    }
    public static Long getUserIdFromToken(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim("id").asLong();
    }

}