package com.uichesoh.authservice.security;

import com.uichesoh.authservice.dto.RequestDto;
import com.uichesoh.authservice.entities.AuthUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtProvider {
    @Value("${jwt.secret}")
    private String secret;
    @PostConstruct
    protected void init(){
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }
    @Autowired
    RouteValidator routeValidator;
    public String createToken(AuthUser authUser){
        Map<String, Object> claims = new HashMap<>();
        claims = Jwts.claims().setSubject(authUser.getUsername());
        claims.put("id",authUser.getUserId());
        claims.put("role",authUser.getRole());
        Date now = new Date();
        Date expiringDate = new Date(now.getTime()+3600000);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiringDate)
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();
    }

    public boolean validate(String token, RequestDto requestDto){
        try{
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
        }catch (Exception e){
            return false;
        }
        if(!isAdmin(token) && routeValidator.isAdmin(requestDto)) return false;
        return true;
    }

    public String getUsernameFromToken(String token){
        try{
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
        }catch(Exception e){
            return "Bad Token";
        }
    }

    private boolean isAdmin(String token){
        return (Jwts.parser().setSigningKey(secret)).parseClaimsJws(token).getBody().get("role").equals("admin");
    }
}
