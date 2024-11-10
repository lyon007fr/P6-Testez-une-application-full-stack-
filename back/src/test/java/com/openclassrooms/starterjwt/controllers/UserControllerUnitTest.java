package com.openclassrooms.starterjwt.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.openclassrooms.starterjwt.mapper.UserMapper;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.services.UserService;

@ExtendWith(MockitoExtension.class)  // Utilisation de MockitoExtension pour des tests unitaires légers, evite de charger tout le context spring avec @SpringBootTest 
class UserControllerUnitTest {

	@Mock
	private UserService userService;
	
	@Mock
	private UserMapper userMapper;
	
	@InjectMocks
    private UserController  userController;
	
	@Test
	void testFindUserById() throws Exception{
		
		String id =  "2";
		
		when(userService.findById(Long.valueOf(id))).thenReturn(new User());
		
		ResponseEntity<?> response = userController.findById(id);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		verify(userService, times(1)).findById(Long.valueOf(id));
	}

	@Test
	void testUserNotFound() throws Exception{
		
		String id =  "2";
		
		when(userService.findById(Long.valueOf(id))).thenReturn(null);
		
		ResponseEntity<?> response = userController.findById(id);
		
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		verify(userService, times(1)).findById(Long.valueOf(id));
	}
	

	
	@Test
    void testDeleteUserUserNotFound() {
        // Arrange
        String id = "2";
        when(userService.findById(Long.valueOf(id))).thenReturn(null);

        // Act
        ResponseEntity<?> response = userController.save(id);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userService, times(1)).findById(Long.valueOf(id));
        verify(userService, never()).delete(anyLong()); // Vérifie que delete n'est pas appelé
    }
	
	@Test
    void testDeleteUserBadRequest() {
        // Arrange
        String id = "invalidId"; // ID invalide

        // Act
        ResponseEntity<?> response = userController.save(id);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(userService, never()).findById(anyLong()); // Vérifie que findById n'est pas appelé
        verify(userService, never()).delete(anyLong()); // Vérifie que delete n'est pas appelé
    }
	
}
