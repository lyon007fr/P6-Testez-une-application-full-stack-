package com.openclassrooms.starterjwt.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.openclassrooms.starterjwt.mapper.TeacherMapper;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.payload.request.SignupRequest;
import com.openclassrooms.starterjwt.payload.response.MessageResponse;
import com.openclassrooms.starterjwt.services.TeacherService;

@ExtendWith(MockitoExtension.class)  // Utilisation de MockitoExtension pour des tests unitaires légers, evite de charger tout le context spring avec @SpringBootTest
class TeacherControllerUnitTest {

	@Mock
	private TeacherMapper teacherMapper;
	
	@Mock
	private TeacherService teacherservice;
	
	@InjectMocks
	private TeacherController teacherController;
	
	@Test
	void testFindTeacherById() {
		 // Arrange
		String id = "1";
		Teacher teacher = new Teacher();
        // Simule l'envoi d'un vrai teacher
        when(teacherservice.findById(Long.valueOf(id))).thenReturn(teacher);

     
        // Act
        ResponseEntity<?> responseEntity = teacherController.findById(id);

        // Assert
        assertEquals(200, responseEntity.getStatusCodeValue());
        // Vérifie que le UserRepository.save() a bien été appelé
        verify(teacherMapper).toDto(teacher);
        
        
	}
	
	@Test
	void testFindTeacherByIdKo() {
		//Arrange
		String id = "1";
		
		// Simuler le comportement de getById pour retourner une exception
		when(teacherservice.findById(Long.valueOf(id))).thenThrow(new NumberFormatException());
		
		//Act
		ResponseEntity<?> responseEntity = teacherController.findById(id);
		
		//Assert
		assertEquals(400, responseEntity.getStatusCodeValue());
		
	}

	@Test
	void testFindALLTeacher() {
		 // Arrange
		
		List<Teacher> teachers = new ArrayList<>();
        // Simule l'appel l'api
        when(teacherservice.findAll()).thenReturn(teachers);

     
        // Act
        ResponseEntity<?> responseEntity = teacherController.findAll();

        // Assert
        assertEquals(200, responseEntity.getStatusCodeValue());
        // Vérifie que le UserRepository.save() a bien été appelé
        verify(teacherMapper).toDto(teachers);
        
        
	}
}
