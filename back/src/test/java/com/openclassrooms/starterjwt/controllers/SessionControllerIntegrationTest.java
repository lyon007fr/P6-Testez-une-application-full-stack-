package com.openclassrooms.starterjwt.controllers;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Date;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.dto.SessionDto;


@SpringBootTest
@AutoConfigureMockMvc
class SessionControllerIntegrationTest {
	
	@Autowired
	MockMvc mockMvc;

	@Test
	@WithMockUser // Simule un utilisateur authentifié ayant le role USER par défaut
	void testGetAllSession() throws Exception {
		
		//when
		mockMvc.perform(get("/api/session")
		//then
				).andExpect(status().isOk());
		
	}
	
	@Test
	@WithMockUser // Simule un utilisateur authentifié ayant le role USER par défaut
	void testGetSessionById() throws Exception {
		
		//Arrange
		Long id = (long) 1;
		//when
		mockMvc.perform(get("/api/session/{id}",id)
		//then
				).andExpectAll(status().isOk(),
                		content().contentType(MediaType.APPLICATION_JSON),
                		jsonPath("$.name").value("Session matinal Yoga"));
		
	}
	
	@Test
	@WithMockUser // Simule un utilisateur authentifié ayant le role USER par défaut
	void testGetSessionBy_Id_Does_Not_Exist() throws Exception {
		
		//Arrange
		Long id = (long) 10;
		//when
		mockMvc.perform(get("/api/session/{id}",id)
		//then
		).andExpectAll(status().isNotFound());
		
	}
	
	@Test
	@WithMockUser // Simule un utilisateur authentifié ayant le role USER par défaut
	void testCreateSession() throws Exception {
		
		
		//Arrange
		SessionDto sessionDto = new SessionDto();
		sessionDto.setName("Creation session via test int");
		sessionDto.setDate(new Date());
		sessionDto.setTeacher_id(1L);
		sessionDto.setUsers(null);
		sessionDto.setDescription("Ajout d'une session via un test IT");
		
		//utilisation d'objectmapper pour créer le json
		ObjectMapper objectMapper = new ObjectMapper();
	    String sessionDtoJson = objectMapper.writeValueAsString(sessionDto);
		
		//when
		mockMvc.perform(post("/api/session")
				.contentType("application/json")
                .content(sessionDtoJson))
		//then
				.andExpectAll(status().isOk(),
						content().contentType(MediaType.APPLICATION_JSON),
						jsonPath("$.name").value(sessionDto.getName()),
						jsonPath("$.teacher_id").value(sessionDto.getTeacher_id()),
						jsonPath("$.description").value(sessionDto.getDescription()));
		
	}
	
	@Test
	@WithMockUser // Simule un utilisateur authentifié ayant le role USER par défaut
	void testUpdateSession() throws Exception {
		
		
		//Arrange
		SessionDto sessionDto = new SessionDto();
		sessionDto.setName("Mise à jour de la session");
		sessionDto.setDate(new Date());
		sessionDto.setTeacher_id(2L);
		sessionDto.setUsers(null);
		sessionDto.setDescription("Update de la session");
		
		Long sessionId = (long) 5;
		
		//utilisation d'objectmapper pour créer le json
		ObjectMapper objectMapper = new ObjectMapper();
	    String sessionDtoJson = objectMapper.writeValueAsString(sessionDto);
		
		//when
		mockMvc.perform(put("/api/session/{sessionId}",sessionId)
				.contentType("application/json")
                .content(sessionDtoJson))
		//then
				.andExpectAll(status().isOk(),
						content().contentType(MediaType.APPLICATION_JSON),
						jsonPath("$.name").value(sessionDto.getName()),
						jsonPath("$.teacher_id").value(sessionDto.getTeacher_id()),
						jsonPath("$.description").value(sessionDto.getDescription()));
		
	}
	
	@Test
	@WithMockUser // Simule un utilisateur authentifié ayant le role USER par défaut
	void testDeleteSession() throws Exception {
		//Arrange
		Long sessionIdToDelete = (long) 2;
		
		//when
				mockMvc.perform(delete("/api/session/{sessionIdToDelete}",sessionIdToDelete))
		//then
						.andExpect(status().isOk());
						
						
						
	}
	
	@Test
	@WithMockUser // Simule un utilisateur authentifié ayant le role USER par défaut
	void testParticipateUserSession() throws Exception {
		//Arrange
		Long sessionIdParticipate = (long) 3;
		Long userId = (long) 2;
		
		//when
				mockMvc.perform(post("/api/session/{sessionIdParticipate}/participate/{userId}",sessionIdParticipate,userId))
		//then
						.andExpect(status().isOk());
						
						
						
	}
	
	
	@Test
	@WithMockUser // Simule un utilisateur authentifié ayant le role USER par défaut
	void testUserAlreadyParticipateToTheSession() throws Exception {
		//la session avec l'id 3 a déjà l'utilisateur avec l'id 1 en tant que participant
		//Arrange
		Long sessionIdParticipate = (long) 3;
		Long userId = (long) 1;
		
		//when
				mockMvc.perform(post("/api/session/{sessionIdParticipate}/participate/{userId}",sessionIdParticipate,userId))
		//then
						.andExpect(status().isBadRequest());
	}		
	
	@Test
	@WithMockUser // Simule un utilisateur authentifié ayant le role USER par défaut
	void testUserNoLongerParticipateToTheSession() throws Exception {
		//la session avec l'id 3 a l'utilisateur avec l'id 1 en tant que participant
		//Arrange
		Long sessionIdParticipate = (long) 6;
		Long userId = (long) 4;
		
		//when
				mockMvc.perform(delete("/api/session/{sessionIdParticipate}/participate/{userId}",sessionIdParticipate,userId))
		//then
						.andExpect(status().isOk());
	}	
	
	
	@Test
	@WithMockUser // Simule un utilisateur authentifié ayant le role USER par défaut
	void testUserAlreadyNoLongerParticipateToTheSession() throws Exception {
		//la session avec l'id 3 a l'utilisateur avec l'id 1 en tant que participant
		//Arrange
		Long sessionIdParticipate = (long) 3;
		Long userId = (long) 2; //ce user ne participe déjà pas à la session
		
		//when
				mockMvc.perform(delete("/api/session/{sessionIdParticipate}/participate/{userId}",sessionIdParticipate,userId))
		//then
						.andExpect(status().isBadRequest());
	}	
	
}
