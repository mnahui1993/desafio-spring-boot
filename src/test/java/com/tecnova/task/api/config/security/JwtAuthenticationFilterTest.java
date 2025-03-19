package com.tecnova.task.api.config.security;

import com.tecnova.task.api.service.authentication.JwtService;
import com.tecnova.task.api.service.authentication.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JwtAuthenticationFilterTest {


    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserService userService;

    @Mock
    private HandlerExceptionResolver handlerExceptionResolver;

    @Mock
    private FilterChain filterChain;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private UserDetails userDetails;


    @BeforeEach
    public void setUp() {
        SecurityContextHolder.clearContext();
    }

    @Test
    public void testDoFilterInternalValidToken_ShouldAuthenticateUser() throws ServletException, IOException {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYXJjbyIsImlhdCI6MTc0MjE4ODc2NSwiZXhwIjoxNzQyMTg5MTI1fQ.JonE3zxXBCkEMO9K_XkCqBdOpzuWGioFdujyPhTm-aE";
        String userName = "marco";


        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtService.extractUsername(token)).thenReturn(userName);
        when(userService.loadUserByUsername(userName)).thenReturn(userDetails);
        when(jwtService.isTokenValid(token, userDetails)).thenReturn(true);


        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);


    }

    @Test
    public void testDoFilterInternalInValidTokenShouldAuthenticateUser() throws ServletException, IOException {

        when(request.getHeader("Authorization")).thenReturn("");
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);



    }

    @Test
    public void testDoFilterInternalExceptionShouldBeHandled() throws ServletException, IOException {
        String token = "invalid_jwt_token";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtService.extractUsername(token)).thenThrow(new RuntimeException());


        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);



    }


}
