package com.tecnova.task.api.controller;


import com.tecnova.task.api.generated.AuthApi;
import com.tecnova.task.api.service.authentication.AuthenticationService;
import com.tecnova.task.api.service.authentication.JwtService;
import com.tecnova.task.api.util.TaskUtilTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AuthenticationControllerTest {


    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private JwtService jwtService;

    @Spy
    private AuthApi api;

    @InjectMocks
    private AuthenticationController authenticationController;

    private MockMvc mockMvc;

    private String jwtToken;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build();
        jwtToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYXJjbyIsImlhdCI6MTc0MjMwOTUwNCwiZXhwIjoxNzQyMzA5ODY0fQ.Gce56g1gKTNDeW_7Yq65qaPAfKJ4SOT2O2DE1ijbv6o";
    }

    @Test
    void testLoginUseValidCredentials() throws Exception {


        when(authenticationService.authenticate(any())).thenReturn(TaskUtilTest.buildUser());
        when(jwtService.generateToken(any())).thenReturn(jwtToken);
        when(jwtService.getExpirationTime()).thenReturn(360000L);

        String request= Files.readString(Path.of("src/test/resources/authorization/request/login_request.json"));
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(jwtToken))
                .andExpect(jsonPath("$.expires_in").value(360000));


    }


}
