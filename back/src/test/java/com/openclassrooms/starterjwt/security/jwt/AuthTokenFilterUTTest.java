package com.openclassrooms.starterjwt.security.jwt;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.openclassrooms.starterjwt.security.services.UserDetailsServiceImpl;

public class AuthTokenFilterUTTest {

    @InjectMocks
    private AuthTokenFilter authTokenFilter;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    private static final String JWT = "valid.jwt.token";
    private static final String USERNAME = "testuser";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDoFilterInternal_WithValidJwt_ShouldSetAuthentication() throws Exception {
        // Mock the JWT behavior
        when(request.getHeader("Authorization")).thenReturn("Bearer " + JWT);
        when(jwtUtils.validateJwtToken(JWT)).thenReturn(true);
        when(jwtUtils.getUserNameFromJwtToken(JWT)).thenReturn(USERNAME);
        
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetailsService.loadUserByUsername(USERNAME)).thenReturn(userDetails);

        // Call the filter method
        authTokenFilter.doFilterInternal(request, response, filterChain);

        // Verify that authentication is set in the SecurityContext
        ArgumentCaptor<UsernamePasswordAuthenticationToken> authenticationCaptor = 
            ArgumentCaptor.forClass(UsernamePasswordAuthenticationToken.class);
        verify(filterChain).doFilter(request, response);
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    public void testDoFilterInternal_WithInvalidJwt_ShouldNotSetAuthentication() throws Exception {
        // Mock the JWT behavior
        when(request.getHeader("Authorization")).thenReturn("Bearer " + JWT);
        when(jwtUtils.validateJwtToken(JWT)).thenReturn(false);

        // Call the filter method
        authTokenFilter.doFilterInternal(request, response, filterChain);

        // Verify that authentication is not set in the SecurityContext
        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    public void testDoFilterInternal_WithoutJwt_ShouldNotSetAuthentication() throws Exception {
        // Mock request without JWT
        when(request.getHeader("Authorization")).thenReturn(null);

        // Call the filter method
        authTokenFilter.doFilterInternal(request, response, filterChain);

        // Verify that authentication is not set in the SecurityContext
        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
    }
}
