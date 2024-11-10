package com.openclassrooms.starterjwt.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.models.User;

@ExtendWith(MockitoExtension.class)
class UserMapperImplUnitTest {

    @InjectMocks
    private UserMapperImpl userMapperImpl;

    private UserDto userDto;
    private User user;
    private List<UserDto> userDtos;
    private List<User> users;

    @BeforeEach
    void setup() {
        // Initialiser UserDto
        userDto = new UserDto();
        userDto.setId(1L);
        userDto.setFirstName("John");
        userDto.setLastName("Doe");
        userDto.setEmail("john.doe@example.com");
        userDto.setPassword("password123");
        userDto.setAdmin(true);
        userDto.setCreatedAt(LocalDateTime.now());
        userDto.setUpdatedAt(LocalDateTime.now());

        // Initialiser User
        user = User.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Smith")
                .email("jane.smith@example.com")
                .password("securePassword")
                .admin(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // Initialiser les listes
        userDtos = new ArrayList<>();
        userDtos.add(userDto);

        users = new ArrayList<>();
        users.add(user);
    }

    @Test
    void testToEntityWhenDtoIsNull() {
        // Act
        User result = userMapperImpl.toEntity((UserDto)null);

        // Assert
        assertNull(result);
    }

    @Test
    void testToEntityWhenDtoIsNotNull() {
        // Act
        User result = userMapperImpl.toEntity(userDto);

        // Assert
        assertNotNull(result);
        assertEquals(userDto.getId(), result.getId());
        assertEquals(userDto.getFirstName(), result.getFirstName());
        assertEquals(userDto.getLastName(), result.getLastName());
        assertEquals(userDto.getEmail(), result.getEmail());
        assertEquals(userDto.getPassword(), result.getPassword());
        assertEquals(userDto.isAdmin(), result.isAdmin());
        assertEquals(userDto.getCreatedAt(), result.getCreatedAt());
        assertEquals(userDto.getUpdatedAt(), result.getUpdatedAt());
    }

    @Test
    void testToDtoWhenEntityIsNull() {
        // Act
        UserDto result = userMapperImpl.toDto((User)null);

        // Assert
        assertNull(result);
    }

    @Test
    void testToDtoWhenEntityIsNotNull() {
        // Act
        UserDto result = userMapperImpl.toDto(user);

        // Assert
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getFirstName(), result.getFirstName());
        assertEquals(user.getLastName(), result.getLastName());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getPassword(), result.getPassword());
        assertEquals(user.isAdmin(), result.isAdmin());
        assertEquals(user.getCreatedAt(), result.getCreatedAt());
        assertEquals(user.getUpdatedAt(), result.getUpdatedAt());
    }

    @Test
    void testToEntityListWhenDtoListIsNull() {
        // Act
        List<User> result = userMapperImpl.toEntity((List<UserDto>) null);

        // Assert
        assertNull(result);
    }

    @Test
    void testToEntityListWhenDtoListIsNotNull() {
        // Act
        List<User> result = userMapperImpl.toEntity(userDtos);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(userDtos.get(0).getId(), result.get(0).getId());
    }

    @Test
    void testToDtoListWhenEntityListIsNull() {
        // Act
        List<UserDto> result = userMapperImpl.toDto((List<User>) null);

        // Assert
        assertNull(result);
    }

    @Test
    void testToDtoListWhenEntityListIsNotNull() {
        // Act
        List<UserDto> result = userMapperImpl.toDto(users);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(users.get(0).getId(), result.get(0).getId());
    }
}
