package com.tecnova.task.api.service;

import com.tecnova.task.api.repository.UserRepository;
import com.tecnova.task.api.service.authentication.AuthenticationService;
import com.tecnova.task.api.util.TaskUtilTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
 class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authenticationService;


    @Test
    void testAuthenticateSuccess() {

        when(authenticationManager.authenticate(any()))
                .thenReturn(mock(Authentication.class));


        when(userRepository.findByUserName(any()))
                .thenReturn(Optional.of(TaskUtilTest.buildUser()));

        var result = authenticationService.authenticate(TaskUtilTest.buildUserRequest());


        assertNotNull(result);
        assertEquals("marco", result.getUsername());

    }

    @Test
    void testAuthenticateUserNotFound() {

        when(authenticationManager.authenticate(any()))
                .thenReturn(mock(Authentication.class));

        when(userRepository.findByUserName(any()))
                .thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> authenticationService.authenticate(TaskUtilTest.buildUserRequest()));

    }

    @Test
    void testGetCurrentUser() {

        Authentication mockAuthentication = mock(Authentication.class);
        when(mockAuthentication.getPrincipal()).thenReturn(TaskUtilTest.buildUser());
        SecurityContextHolder.getContext().setAuthentication(mockAuthentication);

        var result = authenticationService.getCurrentUser();

        assertNotNull(result);
        assertEquals("marco", result.getUsername());
    }
}
