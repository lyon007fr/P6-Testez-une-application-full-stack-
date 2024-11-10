package com.openclassrooms.starterjwt.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD) // Indique que le contexte Spring doit être rechargé avant chaque méthode de test
class TeacherControllerIntegrationTest {
	
	@Autowired
	MockMvc mockMvc;

	@Test
	@WithMockUser 
	void testFindAllTeacher() throws Exception {
		//when
				mockMvc.perform(get("/api/teacher")
				//then
						).andExpectAll(status().isOk(),
		                		content().contentType(MediaType.APPLICATION_JSON),
		                		jsonPath("$[0].lastName").value("DELAHAYE"));
				
	}
	
	@Test
	@WithMockUser 
	void testFindTeacherById() throws Exception {
		//Arrange
		String id = "1";
		//when
				mockMvc.perform(get("/api/teacher/{id}",id)
				//then
						).andExpectAll(status().isOk(),
		                		content().contentType(MediaType.APPLICATION_JSON),//le format attendu est un json
		                		jsonPath("$").isNotEmpty(),//evalue si le json retourné est vide
		                		jsonPath("$.lastName").value("DELAHAYE"));//vérifie que dans le json la clé lastname a la valeur delahaye
				
	}
	
	@Test
	@WithMockUser 
	void testFindTeacherByIdThatDoesNotExist() throws Exception {
		//Arrange
		String id = "995846";
		//when
				mockMvc.perform(get("/api/teacher/{id}",id)
				//then
						).andExpectAll(status().isNotFound());
		                		
		}
	
	@Test
	@WithMockUser 
	void testFindTeacherByIdWithBadFormat() throws Exception {
		//Arrange
		String id = "995846L";
		//when
				mockMvc.perform(get("/api/teacher/{id}",id)
				//then
						).andExpectAll(status().isBadRequest());
		                		
		}
	

}
