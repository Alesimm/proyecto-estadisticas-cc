package com.cc.auth_service.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JwtUtil {

    private static final String SECRET = "clave-super-secreta-para-jwt-2025-123456";
    private static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    // 1h expira
    private static final long EXPIRATION_TIME = 3600000;

    public static String generarToken(String correo, String rol) {
        return Jwts.builder()
                .setSubject(correo)
                .claim("rol", rol) // Guardamos el rol dentro del token
                .setIssuedAt(new Date()) // fecha creacion
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Fecha de expiración
                .signWith(KEY) // firmamos con la clave secreta de cc
                .compact();
    }
}