package com.openclassrooms.starterjwt.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.services.UserService;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD) // Indique que le contexte Spring doit être rechargé avant chaque méthode de test
class UserControllerIntegrationTest {

	
	@InjectMocks
	private UserController userController; // inject les mock dans le controller qu'on test
	
	@Autowired
	MockMvc mockMvc;
	
	@Mock
	private UserService userService;
	
	
	
	@Test
	@WithMockUser
	void testGetUserByID() throws Exception {
		
		//Arrange
				Long id = (long) 1;
				
				mockMvc.perform(get("/api/user/{id}",id)
						).andExpectAll(status().isOk(),
		                		content().contentType(MediaType.APPLICATION_JSON),
		                		jsonPath("$.admin").value(true));
		
	}
	
	@Test
	@WithMockUser
	void testGetUserWithInvalidID() throws Exception {
	    
	    // Arrange
	    String invalidId = "invalid_id"; // Un ID non valide qui génère un NumberFormatException
	    
	    
	    mockMvc.perform(get("/api/user/{id}", invalidId)
	    		.contentType(MediaType.APPLICATION_JSON))
	           .andExpect(status().isBadRequest()); // Vérifier que le statut est 400 (Bad Request)
	}
	
	@Test
	@WithMockUser
	void testGetUserNotFound() throws Exception {
	    
	    // Arrange
	    Long id = 6589L; // Un ID valide mais inexistant
	    when(userService.findById(id)).thenReturn(null); // Simuler que l'utilisateur n'est pas trouvé
	    
	    
	    mockMvc.perform(get("/api/user/{id}", id)
	    		.contentType(MediaType.APPLICATION_JSON))
	           .andExpect(status().isNotFound()); // Vérifier que le statut est 404 (Not Found)
	}
	
	@Test
	@WithMockUser
	void testDeleteUserNotFound() throws Exception {
	    
	    // Arrange
	    Long id = 6589L; // Un ID valide mais inexistant
	    when(userService.findById(id)).thenReturn(null); // Simuler que l'utilisateur n'est pas trouvé
	    
	    
	    mockMvc.perform(delete("/api/user/{id}", id)
	    		.contentType(MediaType.APPLICATION_JSON))
	           .andExpect(status().isNotFound()); // Vérifier que le statut est 404 (Not Found)
	}
	
	@Test
	@WithMockUser(username = "testuser@example.com") // Simule un utilisateur authentifié avec l'email "testuser@example.com"
	void testDeleteUserUnauthorized() throws Exception {
	    
	    // Arrange
	    String id = "1"; // Un ID valide pour l'utilisateur
	    User user = new User();
	    user.setId(Long.valueOf(id));
	    user.setEmail("otheruser@example.com"); // Un autre utilisateur qui a un email différent de l'utilisateur authentifié

	    // Simuler que le service trouve l'utilisateur avec cet ID
	    when(userService.findById(Long.valueOf(id))).thenReturn(user);

	    
	    mockMvc.perform(delete("/api/user/{id}", id)
	    		.contentType(MediaType.APPLICATION_JSON))
	           .andExpect(status().isUnauthorized()); // Vérifier que le statut est 401 UNAUTHORIZED
	}
	
	@Test
	@WithMockUser
	void testDeleteUserWithInvalidID() throws Exception {
	    
	    // Arrange
	    String invalidId = "invalid_id"; // Un ID non valide qui génère un NumberFormatException
	    
	    
	    mockMvc.perform(delete("/api/user/{id}", invalidId)
	    		.contentType(MediaType.APPLICATION_JSON))
	           .andExpect(status().isBadRequest()); // Vérifier que le statut est 400
	}
	
	@Test
	@WithMockUser(username = "test@studi.com") // Simule un utilisateur authentifié avec un l'email
	void testDeleteUser() throws Exception {
	    
		// Arrange
	    String id = "2"; // Un ID valide
	    User user = new User();
	    user.setId(Long.valueOf(id)); // Définir l'ID
	    user.setEmail("test@studi.com"); // Correspond à l'utilisateur authentifié
	    
	    when(userService.findById(Long.valueOf(id))).thenReturn(user);
	    
	    // Act - Exécuter la requête DELETE
	    mockMvc.perform(delete("/api/user/{id}", id)
	    		.contentType(MediaType.APPLICATION_JSON))
	           	.andExpect(status().isOk()); // Vérifier que le statut est 200 OK
	    
	    
	}
		

}
