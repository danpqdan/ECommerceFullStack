package com.apiecommerce.apiecomerce.server.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.apiecommerce.apiecomerce.client.entities.data.AuthenticationDTO;
import com.apiecommerce.apiecomerce.server.entities.Usuario;
import com.apiecommerce.apiecomerce.server.entities.data.LoginResponseDTO;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.UnsupportedJwtException;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generatedToken(Usuario user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("apiecomerce")
                    .withSubject(user.getUsername())
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException e) {
            throw new RuntimeException("Error while generation token", e);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("apiecomerce")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            return "";
        }
    }

    private Instant genExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    // Extrair o username (subject) do token
    public String extrairUsuario(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        DecodedJWT decodedJWT = JWT.require(algorithm)
                .build()
                .verify(token);

        return decodedJWT.getSubject();
    }

    public boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    public boolean validateToken(String token, String username) {
        try {
            String usernameToken = extrairUsuario(token);
            return (username.equals(usernameToken) && !isTokenExpired(token));
        } catch (SignatureException | ExpiredJwtException | UnsupportedJwtException e) {
            // Exceções comuns de um token inválido
            return false;
        }
    }

}
