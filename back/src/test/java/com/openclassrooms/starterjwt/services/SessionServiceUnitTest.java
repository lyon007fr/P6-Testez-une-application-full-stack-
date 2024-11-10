package com.openclassrooms.starterjwt.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.openclassrooms.starterjwt.exception.NotFoundException;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.repository.UserRepository;
import com.openclassrooms.starterjwt.exception.BadRequestException;

class SessionServiceUnitTest {

	@Mock
    private SessionRepository sessionRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private SessionService sessionService;  // La classe contenant la méthode participate
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialisation des mocks
    }
    
    @Test
    public void testParticipateSessionNotFound() {
        // Arrange
        Long sessionId = 1L;
        Long userId = 1L;

        when(sessionRepository.findById(sessionId)).thenReturn(Optional.empty());
        when(userRepository.findById(userId)).thenReturn(Optional.of(new User())); // Simule un utilisateur valide

        // Act & Assert
        assertThrows(NotFoundException.class, () -> {
            sessionService.participate(sessionId, userId);
        });

        // on vérifie que la méthode `findById` du repository a été appelée avec les bons arguments
        verify(sessionRepository, times(1)).findById(sessionId);
        verify(userRepository, times(1)).findById(userId);
    }
    
  
    
    @Test
    public void testParticipateSuccess() {
        // Arrange
        Long sessionId =  1L;
        Long userId =  1L;

        Session session = new Session();
        session.setUsers(new ArrayList<>());
        User user = new User();

        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        sessionService.participate(sessionId, userId);

        // Assert
        verify(sessionRepository, times(1)).findById(sessionId);
        verify(userRepository, times(1)).findById(userId);
        
    }
    
    
    @Test
    public void testNoLongerParticipate_SessionNotFound_ShouldThrowNotFoundException() {
        // Arrange
        Long sessionId = 1L;
        Long userId = 2L;
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.empty()); // Simule une session non trouvée

        // Act & Assert
        assertThrows(NotFoundException.class, () -> sessionService.noLongerParticipate(sessionId, userId));
    }
    
    @Test
    public void testNoLongerParticipate_MultipleUsers_ShouldRemoveSpecificUser() {
        // Arrange
        Long sessionId = 1L;
        Long userIdToRemove = 2L;

        User user1 = new User();
        user1.setId(1L);
        User user2 = new User();
        user2.setId(userIdToRemove);
        User user3 = new User();
        user3.setId(3L);

        Session session = new Session();
        session.setId(sessionId);
        session.setUsers(new ArrayList<>(List.of(user1, user2, user3))); // Plusieurs utilisateurs participent

        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session)); // Simule une session trouvée

        // Act
        sessionService.noLongerParticipate(sessionId, userIdToRemove);

        // Assert
        assertEquals(2, session.getUsers().size()); // Vérifie que deux utilisateurs restent
        assertFalse(session.getUsers().stream().anyMatch(user -> user.getId().equals(userIdToRemove))); // Vérifie que l'utilisateur a été supprimé
        verify(sessionRepository).save(session); // Vérifie que la session a été sauvegardée
    }
    
    @Test
    public void testNoLongerParticipate_UserParticipating_ShouldRemoveUser() {
        // Arrange
        Long sessionId = 1L;
        Long userId = 2L;

        User user = new User();
        user.setId(userId);

        Session session = new Session();
        session.setId(sessionId);
        session.setUsers(new ArrayList<>(List.of(user))); // L'utilisateur participe à la session

        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session)); // Simule une session trouvée

        // Act
        sessionService.noLongerParticipate(sessionId, userId);

        // Assert
        assertTrue(session.getUsers().isEmpty()); // L'utilisateur doit être supprimé de la liste des utilisateurs
        verify(sessionRepository).save(session); // Vérifie que la session a été sauvegardée
    }
    
    @Test
    public void testNoLongerParticipate_UserNotParticipating_ShouldThrowBadRequestException() {
        // Arrange
        Long sessionId = 1L;
        Long userId = 2L;
        Session session = new Session();
        session.setId(sessionId);
        session.setUsers(new ArrayList<>()); // Aucun utilisateur ne participe à la session

        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session)); // Simule une session trouvée

        // Act & Assert
        assertThrows(BadRequestException.class, () -> sessionService.noLongerParticipate(sessionId, userId));
    }
    
}
