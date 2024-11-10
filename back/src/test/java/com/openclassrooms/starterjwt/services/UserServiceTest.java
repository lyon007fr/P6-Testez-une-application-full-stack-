package com.openclassrooms.starterjwt.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;


class UserServiceTest {

	// Création du mock du UserRepository
	@Mock
	private UserRepository userRepository;
	
	private UserService underTest;
	
	// Injection automatique du mock userRepository dans userService
	@InjectMocks
    private UserService userService;
	
	//Avant chaque test initialisation des mocks dans la class testé (ici this ==> userService)
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		underTest = new UserService(userRepository);
	}
	
	

	@Test
	void testDelete() {
		//given
		Long id = (long) 1;
		User mockUser = new User();
		mockUser.setId(id);
		
		
		//when
		underTest.delete(mockUser.getId());
		
		//then
		verify(userRepository,times(1)).deleteById(mockUser.getId());
	}

	@Test
	void testFindById_UserExist() {
		//given
		Long id = (long) 1;
		User mockUser = new User();
		mockUser.setId(id);
		when(userRepository.findById(mockUser.getId())).thenReturn(Optional.of(mockUser));
		
		//when
		User result = underTest.findById(mockUser.getId());
		
				
		//then
		verify(userRepository,times(1)).findById(mockUser.getId());
		assertThat(result.getId()).isEqualTo(mockUser.getId());
	}
	
	@Test
	void testFindById_UserDoesNotExist() {
		//given
		Long id = (long) 1;
		when(userRepository.findById(id)).thenReturn(Optional.empty());
		
		//when
		User result  = underTest.findById(id);
		
		//then
		verify(userRepository,times(1)).findById(id);
		assertThat(result).isEqualTo(null);
	}
		
}
