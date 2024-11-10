package com.openclassrooms.starterjwt.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;


import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.payload.request.SignupRequest;
import com.openclassrooms.starterjwt.payload.response.MessageResponse;
import com.openclassrooms.starterjwt.repository.UserRepository;

@ExtendWith(MockitoExtension.class)  // Utilisation de MockitoExtension pour des tests unitaires légers, evite de charger tout le context spring avec @SpringBootTest 
class AuthControllerUnitTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthController authController;

    @Test
    void testRegisterSuccess() {
        // Arrange
        // Simule qu'aucun utilisateur n'existe déjà avec cet email
        when(userRepository.existsByEmail(anyString())).thenReturn(false);

        // Simule l'encodage du mot de passe
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("newuser@test.com");
        signupRequest.setFirstName("First");
        signupRequest.setLastName("Last");
        signupRequest.setPassword("password");

        // Act
        ResponseEntity<?> responseEntity = authController.registerUser(signupRequest);

        // Assert
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals("User registered successfully!", ((MessageResponse) responseEntity.getBody()).getMessage());
        // Vérifie que le UserRepository.save() a bien été appelé
        verify(userRepository).save(any(User.class));
        verify(passwordEncoder).encode(anyString());
    }
}
