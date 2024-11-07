package com.repinsky.task_tracker_backend.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.repinsky.task_tracker_backend.constants.Constant.ROLES;


@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "app")
public class JWTTokenUtil {

    private String jwtSecret;

    private long jwtExpiration;

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        List<String> rolesList = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        claims.put(ROLES.getValue(), rolesList);

        Date issueDate = new Date();
        Date expirationDate = new Date(issueDate.getTime() + jwtExpiration);

        return Jwts.builder()
                .setClaims(claims)                     //  user roles
                .setSubject(userDetails.getUsername())
                .setIssuedAt(issueDate)                                   //  creation datetime
                .setExpiration(expirationDate)                                 //  expiry datetime
                .signWith(SignatureAlgorithm.HS256, jwtSecret)                   //  signature
                .compact();

    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUserDataFromToken(String token){
        return getAllClaimsFromToken(token).getSubject();
    }

    public String validateJwtToken(String authToken) {
        Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
        return "";
    }
}
