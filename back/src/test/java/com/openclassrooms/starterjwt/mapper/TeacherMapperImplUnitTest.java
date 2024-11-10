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
import org.springframework.beans.factory.annotation.Autowired;

import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.models.Teacher;


@ExtendWith(MockitoExtension.class)
class TeacherMapperImplUnitTest {
	
	@InjectMocks
	private TeacherMapperImpl teacherMapperImpl;
	
	 
	 private TeacherDto teacherDto;// Variable d'instance
	 private Teacher teacher;// Variable d'instance
	 
	 private List<TeacherDto> teachersDto; // Variable d'instance
	 private List<Teacher> teachers;       // Variable d'instance
	 
	 
	 @BeforeEach
	 public void setup() {
        // Arrange: Créer un TeacherDto pour les tests
		teacherDto = new TeacherDto();
        teacherDto.setId(1L);
        teacherDto.setLastName("Test");
        teacherDto.setFirstName("Toto");
        teacherDto.setCreatedAt(LocalDateTime.now());
        teacherDto.setUpdatedAt(LocalDateTime.now());
        teachersDto = new ArrayList<>();
        teachersDto.add(teacherDto);
        
        teacher = new Teacher();
        teacher.setId(2L);
        teacher.setLastName("Test");
        teacher.setFirstName("Toto");
        teacher.setCreatedAt(LocalDateTime.now());
        teacher.setUpdatedAt(LocalDateTime.now());
        teachers = new ArrayList<>();
        teachers.add(teacher);
        
	    }

		  
	
	@Test
	void testWhenDtoIsNullToEntity() {
		//Act		
		Teacher result = teacherMapperImpl.toEntity((TeacherDto) null);
		
		assertEquals(null, result); //assertNull(result) fonctionne aussi
		
	}
	
	@Test
	void testWhenDtoIsNotNullToEntity() {
		//Act
		Teacher result = teacherMapperImpl.toEntity(teacherDto);
		
		assertNotNull(result);
		assertEquals(teacherDto.getId(), result.getId());
		assertEquals(teacherDto.getLastName(), result.getLastName());
		assertEquals(teacherDto.getFirstName(), result.getFirstName());
		assertEquals(teacherDto.getCreatedAt(), result.getCreatedAt());
		assertEquals(teacherDto.getUpdatedAt(), result.getUpdatedAt());
		
	}
	
	@Test
	void testWhenListDtoIsNullToEntity() {
		//Act		
		List<Teacher> result = teacherMapperImpl.toEntity((List<TeacherDto>)null);
		
		assertEquals(null, result); //assertNull(result) fonctionne aussi
		
	}
	
	@Test
	void testWhenListDtoIsNotNullToEntity() {
		//Act		
		List<Teacher> result = teacherMapperImpl.toEntity(teachersDto); //declarer teachersDto comme variable de class pour y accéder
		
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(teacherDto.getId(), result.get(0).getId());
		assertEquals(teacherDto.getLastName(), result.get(0).getLastName());
		assertEquals(teacherDto.getFirstName(), result.get(0).getFirstName());
	    assertEquals(teacherDto.getCreatedAt(), result.get(0).getCreatedAt());
	    assertEquals(teacherDto.getUpdatedAt(), result.get(0).getUpdatedAt());
	}
	
	
	
	@Test
	void testWhenEntityIsNullToDto() {
		//Act		
		TeacherDto result = teacherMapperImpl.toDto((Teacher) null);
		
		assertEquals(null, result); //assertNull(result) fonctionne aussi
		
	}
	
	@Test
	void testWhenListEntityIsNullToDto() {
		//Act		
		List<TeacherDto> result = teacherMapperImpl.toDto((List<Teacher>)null);
		
		assertEquals(null, result); //assertNull(result) fonctionne aussi
		
	}
	
	
	
	@Test
	void testWhenEntityIsNotNullToDto() {
		//Act
		TeacherDto result = teacherMapperImpl.toDto(teacher);
		
		assertNotNull(result);
		assertEquals(teacher.getId(), result.getId());
		assertEquals(teacher.getLastName(), result.getLastName());
		assertEquals(teacher.getFirstName(), result.getFirstName());
		assertEquals(teacher.getCreatedAt(), result.getCreatedAt());
		assertEquals(teacher.getUpdatedAt(), result.getUpdatedAt());
		
	}

	
	
	@Test
	void testWhenListEntityIsNotNullToDto() {
		//Act		
		List<TeacherDto> result = teacherMapperImpl.toDto(teachers); //declarer teachersDto comme variable de class pour y accéder
		
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(teacher.getId(), result.get(0).getId());
		assertEquals(teacher.getLastName(), result.get(0).getLastName());
		assertEquals(teacher.getFirstName(), result.get(0).getFirstName());
	    assertEquals(teacher.getCreatedAt(), result.get(0).getCreatedAt());
	    assertEquals(teacher.getUpdatedAt(), result.get(0).getUpdatedAt());
	}
	
}
