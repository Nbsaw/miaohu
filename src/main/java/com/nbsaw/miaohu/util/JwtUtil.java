package com.nbsaw.miaohu.util;

import com.nbsaw.miaohu.exception.ExJwtException;
import com.nbsaw.miaohu.exception.InValidJwtException;
import com.nbsaw.miaohu.type.UserType;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;
import java.util.logging.Level;

/**
 * Created by nbsaw on 2017/6/9.
 */

@Component
public class JwtUtil {
    private int timeout;
    private String key;

    @Autowired
    private JwtUtil(@Value("${jwt.timeout}") int timeout,@Value("${jwt.key}") String key) {
        this.timeout = timeout;
        this.key = key;
    }

    // TODO 超时
    // TODO 是否有效
    // TODO 详细的错误声明
    public Claims parse(String token) throws InValidJwtException, ExJwtException {
        Claims claims = null;
        try{
            claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        }catch (MalformedJwtException e){
            throw new InValidJwtException();
        }catch (ExpiredJwtException e){
            throw new ExJwtException();
        }catch (SignatureException e){
            throw new InValidJwtException();
        }
        return claims;
    }

    // 超时
    public Date getExp(){
        return new Date(new Date().getTime() + timeout);
    }

    // 根据登陆账号密码生成的token
    public String createJWT(String uid,UserType userType){
        String compactJws = Jwts.builder()
                .signWith(SignatureAlgorithm.HS512,key)
                .setExpiration(getExp())
                .claim("uid",uid)
                .claim("userType",userType.toString())  // 用户权限设置
                .compact();
        return compactJws;
    }
}
