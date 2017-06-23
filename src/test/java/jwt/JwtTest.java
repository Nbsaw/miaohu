package jwt;

/**
 * Created by nbsaw on 2017/6/7.
 */
import io.jsonwebtoken.*;

import java.util.Date;
import java.util.UUID;

public class JwtTest {
    public static void main(String[] args) {
        String key = "miohujwt";

        String compactJws = Jwts.builder()
                .setSubject("呵呵")
                .setExpiration(new Date(new Date().getTime() - 86400000))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();

        String compactJws2 = Jwts.builder()
                .setSubject("呵呵")
                .signWith(SignatureAlgorithm.HS512, "asdasd")
                .compact();

        String compactJws3 = Jwts.builder()
                .setSubject("呵呵")
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();

        String compactJws4 = Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .signWith(SignatureAlgorithm.HS512,key).claim("userType","asd")
                .compact();

        // 畸形的token
        try {
            System.out.println(Jwts.parser().setSigningKey(key).parseClaimsJws("asdasd"));
        }catch (MalformedJwtException e){
            System.out.println("啥玩意这是");
        }
        // 超时的token
        try{
            System.out.println(Jwts.parser().setSigningKey(key).parseClaimsJws(compactJws));
        }catch (ExpiredJwtException e){
            System.out.println("已经超时的token");
        }
        // 非官方token
        try{
            System.out.println(Jwts.parser().setSigningKey(key).parseClaimsJws(compactJws2));
        }catch (SignatureException e){
            System.out.println("无法被信任的token");
        }
        // 正常的token
        System.out.println(Jwts.parser().setSigningKey(key).parseClaimsJws(compactJws3));
        System.out.println(Jwts.parser().setSigningKey(key).parseClaimsJws(compactJws4).getBody());
    }
}
