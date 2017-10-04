package com.nbsaw.miaohu.utils;

import com.nbsaw.miaohu.exception.ExJwtException;
import com.nbsaw.miaohu.exception.InValidJwtException;
import com.nbsaw.miaohu.type.UserType;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtUtils {
    private final int timeout;
    private final String key;

    // 初始化
    @Autowired
    public JwtUtils(@Value("${jwt.timeout}") int timeout, @Value("${jwt.key}") String key) {
        this.timeout = timeout;
        this.key = key;
    }

    // 验证超时
    // 验证是否有效
    // TODO 详细的错误声明
    // 解析传过来的token
    public boolean valid(String token) throws InValidJwtException, ExJwtException {
        boolean flag;
        try{
            Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
            flag = true;
        }catch (MalformedJwtException e){
            throw new InValidJwtException();
        }catch (ExpiredJwtException e){
            throw new ExJwtException();
        }
        return flag;
    }

    private Claims parse(String token){
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
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
    public String getUid(String token) {
        return (String) parse(token).get("uid");
    }
}
