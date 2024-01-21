package com.bej.authentication.security;


import com.bej.authentication.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTSecurityTokenGeneratorImpl implements SecurityTokenGenerator {
    public String createToken(User user) {
        // create the Jwt
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getEmail());
        System.out.println(claims + " : " + user.getEmail());
        return generateToken(claims, user.getEmail());
    }

    public String generateToken(Map<String, Object> claims, String subject) {
        // Generate the token and set the email in the claims
        String jwtToken = Jwts.builder().setIssuer("chitranshu")
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "secretkey").compact();
        System.out.println(jwtToken);
        return jwtToken;
    }


}
