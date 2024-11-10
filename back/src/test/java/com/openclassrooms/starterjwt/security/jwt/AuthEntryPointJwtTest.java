package com.openclassrooms.starterjwt.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthEntryPointJwtTest {

    @Mock
    private HttpServletRequest HttpServletRequest;

    @Mock
    private AuthenticationException authenticationException;

    @InjectMocks
    private AuthEntryPointJwt authEntryPointJwt;

    @Test
    void shouldHandleAuthException() throws Exception {
        // Arrange
        String errorMessage = "Unauthorized";
        when(authenticationException.getMessage()).thenReturn(errorMessage);
        when(HttpServletRequest.getServletPath()).thenReturn("/api/test");

        MockHttpServletResponse response = new MockHttpServletResponse();

        // Act
        authEntryPointJwt.commence(HttpServletRequest, response, authenticationException);

        // Assert
        assertEquals(401, response.getStatus());
        assertEquals("application/json", response.getContentType());

        Map<String, Object> responseExpected = new HashMap<>();
        responseExpected.put("status", 401);
        responseExpected.put("error", "Unauthorized");
        responseExpected.put("message", errorMessage);
        responseExpected.put("path", "/api/test");

        String expectedJson = new ObjectMapper().writeValueAsString(responseExpected);

        assertEquals(expectedJson, response.getContentAsString());
    }
}
