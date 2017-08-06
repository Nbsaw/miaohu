package com.nbsaw.miaohu.util;

import com.nbsaw.miaohu.exception.ExJwtException;
import com.nbsaw.miaohu.exception.InValidJwtException;
import com.nbsaw.miaohu.type.UserType;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class JwtUtil {

    private final int timeout;
    private final String key;

    // 初始化
    @Autowired
    private JwtUtil(@Value("${jwt.timeout}") int timeout,@Value("${jwt.key}") String key) {
        this.timeout = timeout;
        this.key = key;
    }

    // 验证超时
    // 验证是否有效
    // TODO 详细的错误声明
    // 解析传过来的token
    public Claims parse(String token) throws InValidJwtException, ExJwtException {
        Claims claims;
        try{
            claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        }catch (MalformedJwtException e){
            throw new InValidJwtException();
        }catch (ExpiredJwtException e){
            throw new ExJwtException();
        }
        return claims;
    }

    // 获取jwt的超时时间
    private Date getExp(){
        return new Date(new Date().getTime() + timeout);
    }

    // 根据用户的id以及用户的类型生成对应的jwt
    public String createJWT(String uid,UserType userType){
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512,key)
                .setExpiration(getExp())
                .claim("uid",uid)
                .claim("userType",userType.toString())  // 用户权限设置
                .compact();
    }

    // 获取用户uid
    public String getUid(HttpServletRequest request) throws ExJwtException, InValidJwtException {
        // 解析token
        String token = request.getHeader("token");
        return (String) parse(token).get("uid");
    }
}
