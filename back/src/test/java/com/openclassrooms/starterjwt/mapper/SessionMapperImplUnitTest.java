package com.openclassrooms.starterjwt.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.models.Session;

@ExtendWith(MockitoExtension.class)
class SessionMapperImplUnitTest {
	
	
	

	@InjectMocks
	private SessionMapperImpl sessionMapperImpl;
		
	
	@Test
	void testListNullToEntity() {
		//Arrange
		List <SessionDto> sessionsEntityList = null;
		
		//Act
		List <Session> result =  sessionMapperImpl.toEntity(sessionsEntityList);
		
		//Assert
		assertEquals(null, result);
	}

	@Test
	void testListToEntity() {
		//Arrange
		List <SessionDto> sessionsEntityList = new ArrayList<>();
		
		SessionDto sessionDtoA = new SessionDto();
		SessionDto sessionDtoB = new SessionDto();
		
		sessionsEntityList.add(sessionDtoA);
		sessionsEntityList.add(sessionDtoB);
		
		//Act
		List <Session> result =  sessionMapperImpl.toEntity(sessionsEntityList);
		
		//Assert
		assertEquals(2, result.size());
	}
	
	
	
	@Test
	void testListNullToDTO() {
		//Arrange
		List <Session> sessionsEntityList = null;
		
		//Act
		List <SessionDto> result =  sessionMapperImpl.toDto(sessionsEntityList);
		
		//Assert
		assertEquals(null, result);
	}

	@Test
	void testListToDTO() {
		//Arrange
		List <Session> sessionsEntityList = new ArrayList<>();
		
		Session sessionDtoA = new Session();
		Session sessionDtoB = new Session();
		
		sessionsEntityList.add(sessionDtoA);
		sessionsEntityList.add(sessionDtoB);
		
		//Act
		List <SessionDto> result =  sessionMapperImpl.toDto(sessionsEntityList);
		
		//Assert
		assertEquals(2, result.size());
	}
	
	@Test
	void testDtoToEntity() {
		//Arrange
		SessionDto sessionDto = new SessionDto();
		sessionDto.setId(20L);
		
		
		//Act
		Session result =  sessionMapperImpl.toEntity(sessionDto);
		
		//Assert
		assertEquals(sessionDto.getId(), result.getId());
	}
	
	@Test
	void testEntityToDtoTo() {
		//Arrange
		Session session = new Session();
		session.setName("Je suis un test");
		
		//Act
		SessionDto result =  sessionMapperImpl.toDto(session);
		
		//Assert
		assertEquals(session.getName(), result.getName());
	}
	
	
}


