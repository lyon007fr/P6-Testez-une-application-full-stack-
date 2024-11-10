package com.openclassrooms.starterjwt.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import com.google.gson.Gson;
import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import com.openclassrooms.starterjwt.payload.request.SignupRequest;
import com.openclassrooms.starterjwt.repository.UserRepository;




@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD) // Indique que le contexte Spring doit être rechargé avant chaque méthode de test
class AuthControllerIntegrationTest {

	@Autowired
	MockMvc mockMvc;
	
	
	
	@Test
	@DisplayName("Test la création d'un compte avec un mail déjà enregistré")
	void testRegisterKO() throws Exception {
		
		SignupRequest SignupRequestKo= new SignupRequest(); 
		SignupRequestKo.setEmail("test@studi.com"); //mail déjà en base
		SignupRequestKo.setFirstName("firstName");
		SignupRequestKo.setLastName("lastName");
		SignupRequestKo.setPassword("helloWorld");
		
		// Création du JSON
	    Gson gson = new Gson();
	    String SignupRequestJsonKo = gson.toJson(SignupRequestKo);
	    
		
	
		mockMvc.perform(post("/api/auth/register")
				.contentType("application/json")
                .content(SignupRequestJsonKo))
                .andExpectAll(status().isBadRequest(),
                		content().contentType(MediaType.APPLICATION_JSON),
                		jsonPath("$.message").value("Error: Email is already taken!"));
             		
	}
	
	@Test
	@DisplayName("Test la création d'un nouveau compte")
	void testRegisterOK() throws Exception {
		SignupRequest SignupRequestOk= new SignupRequest(); 
		SignupRequestOk.setEmail("bonjour@hotmail.fr");
		SignupRequestOk.setFirstName("firstName");
		SignupRequestOk.setLastName("lastName");
		SignupRequestOk.setPassword("helloWorld");
		
		// Création du JSON
	    Gson gson = new Gson();
	    String SignupRequestJsonKo = gson.toJson(SignupRequestOk);
	    
	    mockMvc.perform(post("/api/auth/register")
				.contentType("application/json")
                .content(SignupRequestJsonKo))
                .andExpectAll(status().isOk(),
                		content().contentType(MediaType.APPLICATION_JSON),
                		jsonPath("$.message").value("User registered successfully!"));
	    

	}
	
	@Test
	@DisplayName("Test la connexion avec un compte existant")
	void testLoginSuccess() throws Exception {
		LoginRequest loginRequestSuccess = new LoginRequest(); //user qui sera utilisé pour réussir l'enregistrement et la connexion
		loginRequestSuccess.setEmail("tata@studio.com");
		loginRequestSuccess.setPassword("test256784");
		// Création du JSON
	    Gson gson = new Gson();
	    String loginRequestJson = gson.toJson(loginRequestSuccess);
		
		
		  mockMvc.perform(post("/api/auth/login")
	                .contentType("application/json") // Indiquer que le contenu est du JSON
	                .content(loginRequestJson)) // Passer le JSON en tant que corps de la requête
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$.token", is(notNullValue()))); // Vérifier que le token n'est pas null
		
	}
	

}
