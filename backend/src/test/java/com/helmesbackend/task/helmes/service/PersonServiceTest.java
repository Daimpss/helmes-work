package com.helmesbackend.task.helmes.service;

import com.helmesbackend.task.helmes.dto.PersonDTO;
import com.helmesbackend.task.helmes.dto.SectorDTO;
import com.helmesbackend.task.helmes.model.Person;
import com.helmesbackend.task.helmes.mapper.PersonMapper;
import com.helmesbackend.task.helmes.model.Sector;
import com.helmesbackend.task.helmes.repository.SectorRepository;
import com.helmesbackend.task.helmes.repository.PersonRepository;
import com.helmesbackend.task.helmes.dto.PersonFormDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private SectorRepository sectorRepository;

    @Mock
    private PersonMapper personMapper;

    @InjectMocks
    private PersonService personService;


    @Test
    void PersonService_savePerson_createsPersonWithSectors() {
        // Arrange
        Set<Long> sectorIds = new HashSet<>();
        sectorIds.add(1L);

        Sector sector = Sector.builder()
                .name("Technology")
                .parent(null)
                .children(new HashSet<>())
                .level(0)
                .personSectors(new HashSet<>())
                .build();
        sector.setId(1L);

        PersonFormDTO personFormDTO = PersonFormDTO.builder()
                .name("John Doe")
                .agreeTerms(true)
                .sectorIds(sectorIds)
                .build();

        Person person = Person.builder()
                .name("John Doe")
                .agreeTerms(true)
                .personSectors(new HashSet<>())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        person.setId(1L);

        SectorDTO sectorDTO = SectorDTO.builder()
                .id(1L)
                .name("Technology")
                .level(0)
                .parentId(null)
                .children(new ArrayList<>())
                .build();

        Set<SectorDTO> sectorDTOs = new HashSet<>();
        sectorDTOs.add(sectorDTO);

        PersonDTO testPersonDTO = PersonDTO.builder()
                .id(1L)
                .name("John Doe")
                .agreeTerms(true)
                .sectors(sectorDTOs)  // Only 1 sector
                .build();

        when(personRepository.save(ArgumentMatchers.any())).thenReturn(person);
        when(sectorRepository.findById(1L)).thenReturn(Optional.of(sector));
        when(personMapper.toDto(person)).thenReturn(testPersonDTO);

        // Act
        PersonDTO result = personService.savePerson(personFormDTO);

        // Assert
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        assertEquals(true, result.getAgreeTerms());
        assertEquals(1L, result.getId());
        assertEquals(1, result.getSectors().size());
    }


}
