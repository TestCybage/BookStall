package com.example.bookshop.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@SpringBootTest
class JwtUtilTest {

    @Mock
    private UserDetails userDetails;

    private JwtUtil jwtUtil = new JwtUtil();
    private String username = "testuser";
    private String jwtSecret = "AashaySecret_123";
    private int jwtTokenValidity = 3600 * 5;
    private String jwtToken = Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + jwtTokenValidity * 1000))
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact();

    @Test
    void testGenerateToken() {
        Mockito.when(userDetails.getUsername()).thenReturn(username);
        String token = jwtUtil.generateToken(userDetails);
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        assertEquals(claims.getSubject(), username);
    }

    @Test
    void testGetUserNameFromToken() {
        String actualUsername = jwtUtil.getUserNameFromToken(jwtToken);
        assertEquals(username, actualUsername);
    }

    @Test
    void testGetExpirationDateFromToken() {
        Date actualExpirationDate = jwtUtil.getExpirationDateFromToken(jwtToken);
        Date expectedExpirationDate = new Date(System.currentTimeMillis() + jwtTokenValidity * 1000);
        String actualDate = actualExpirationDate.toString();
        String expectedDate = expectedExpirationDate.toString();
        assertEquals(actualDate, expectedDate);
    }

    @Test
    void testValidateToken() {
        Mockito.when(userDetails.getUsername()).thenReturn(username);
        boolean actualResult = jwtUtil.validateToken(jwtToken, userDetails);
        assertTrue(actualResult);
    }
}
