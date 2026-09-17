package com.afifa.service;
import com.afifa.model.User;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.util.Date;
import javax.crypto.SecretKey;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secret;
    private SecretKey getKey(){
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
    public String generateToken(User user){
       return Jwts.builder()
               .subject(user.getId().toString())
               .claim("email",user.getEmail())
               .issuedAt(new Date())
               .expiration(new Date(System.currentTimeMillis()+1000*60*60))
               .signWith(getKey())
               .compact();
    }
    public String extractEmail(String token){
        return Jwts.parser().
                verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("email",String.class);
    }
}
