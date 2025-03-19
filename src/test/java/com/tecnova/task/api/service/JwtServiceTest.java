package com.tecnova.task.api.service;


import com.tecnova.task.api.service.authentication.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    private UserDetails userDetails;



    @BeforeEach
    void setUp() {

        ReflectionTestUtils.setField(jwtService, "secretKey", "3cfa76ef14937c1c0ea519f8fc057a80fcd04a7420f8e8bcd0a7567c272e007b");
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", 360000);
        userDetails = User.builder()
                .username("marco")
                .password("123")
                .authorities(Collections.emptyList())
                .build();
    }


    @Test
    void testGenerateToken() {
        String token = jwtService.generateToken(userDetails);
        var timeExpiration = jwtService.getExpirationTime();
        assertEquals(timeExpiration,360000);
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void testExtractUsername() {
        String token = jwtService.generateToken(userDetails);
        String username = jwtService.extractUsername(token);
        assertEquals("marco", username);
    }


    @Test
    void testIsTokenValid() {
        String token = jwtService.generateToken(userDetails);
        boolean isValid = jwtService.isTokenValid(token, userDetails);
        assertTrue(isValid);
        UserDetails modifiedUserDetails = new User("xxx", "password", new ArrayList<>());
        isValid = jwtService.isTokenValid(token, modifiedUserDetails);
        assertFalse(isValid);
    }

}
