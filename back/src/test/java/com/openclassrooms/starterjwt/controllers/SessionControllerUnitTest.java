package com.openclassrooms.starterjwt.controllers;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.stubbing.answers.DoesNothing;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Date;
import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.mapper.SessionMapper;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.services.SessionService;


@ExtendWith(MockitoExtension.class)  // Utilisation de MockitoExtension pour des tests unitaires légers, evite de charger tout le context spring avec @SpringBootTest 
class SessionControllerUnitTest {

	
	@Mock
	private SessionService sessionService;
	
	@Mock
	private SessionMapper sessionMapper; 
	
	@Mock
	private SessionDto sessionDto;
	
	@InjectMocks
    private SessionController sessionController;
	
	@Test
	void testGetSessionDoesNotExist() {
		
		String id = "1";
		
		when(sessionService.getById(Long.valueOf(id))).thenReturn(null);
		
		ResponseEntity<?> response = sessionController.findById(id);
		
		//vérifie le code erreur
		assertEquals(404, response.getStatusCodeValue());
		verify(sessionService,times(1)).getById(Long.valueOf(id));
	    
		
		
		
	}
	
	@Test
	void testGetSessionThrowException() {
		String id = "1";
		
		when(sessionService.getById(Long.valueOf(id))).thenThrow(new NumberFormatException());
		
		ResponseEntity<?> response = sessionController.findById(id);
						
	    // Vérifie le statut de la réponse (Bad Request - 400)
	    assertEquals(400, response.getStatusCodeValue());
	    verify(sessionService,times(1)).getById(Long.valueOf(id));
	    
				
	}
	
	@Test
	void testUpdateSessionThrowException() {
		
		String id = "1";
		
		SessionDto sessionDto = new SessionDto();
		sessionDto.setName("Creation session via test unitaire");
		sessionDto.setDate(new Date());
		sessionDto.setTeacher_id(1L);
		sessionDto.setUsers(null);
		sessionDto.setDescription("Ajout d'une session via un test unitaire");
		
		when(sessionService.update(Long.valueOf(id), sessionMapper.toEntity(sessionDto))).thenThrow(new NumberFormatException());
		
		ResponseEntity<?> response = sessionController.update(id,sessionDto);
		
		assertEquals(400,response.getStatusCodeValue());
		verify(sessionService,times(1)).update(Long.valueOf(id),sessionMapper.toEntity(sessionDto));
		
		
	}
	
	@Test
	void testDeleteSessionKo() {
		
		String id = "1";
				
		when(sessionService.getById(Long.valueOf(id))).thenReturn(null);
		
		ResponseEntity<?> response = sessionController.save(id);
		
		assertEquals(404,response.getStatusCodeValue());
		verify(sessionService,times(1)).getById(Long.valueOf(id));
				
	}
	
	
	
	@Test
	void testDeleteSessionSucceed() {
		
		String id = "1";
		Session session = new Session();
		// Simuler le comportement de getById pour retourner une session valide
	    when(sessionService.getById(Long.valueOf(id))).thenReturn(session);
	    // Simuler le comportement de delete
		doNothing().when(sessionService).delete(Long.valueOf(id));
		// Appeler la méthode à tester
		ResponseEntity<?> response = sessionController.save(id);
		// Vérifier que la réponse est OK
		assertEquals(200, response.getStatusCodeValue());
	    // Vérifier que la méthode delete a été appelée une fois avec l'identifiant correct
		verify(sessionService,times(1)).delete(Long.valueOf(id));
	    
				
	}
	
	@Test
	void testDeleteSessionThrowExecption() {
		String id = "1";
		
		// Simuler le comportement de getById pour retourner une exception
		when(sessionService.getById(Long.valueOf(id))).thenThrow(new NumberFormatException());
		// Appeler la méthode à tester
		ResponseEntity<?> response = sessionController.save(id);
		assertEquals(400, response.getStatusCodeValue());
		// Vérifier que la méthode delete a été appelée une fois avec l'identifiant incorrect
		verify(sessionService,times(1)).getById(Long.valueOf(id));
		// Vérifier que la méthode delete n'a jamais été appelée (car l'exception empêche d'y arriver)
	    verify(sessionService, times(0)).delete(anyLong());
	}
	
	
	
	
}
