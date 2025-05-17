package com.helmesbackend.task.helmes.service;

import com.helmesbackend.task.helmes.dto.PersonDTO;
import com.helmesbackend.task.helmes.dto.PersonFormDTO;
import com.helmesbackend.task.helmes.mapper.PersonMapper;
import com.helmesbackend.task.helmes.model.Person;
import com.helmesbackend.task.helmes.model.PersonSector;
import com.helmesbackend.task.helmes.model.Sector;
import com.helmesbackend.task.helmes.repository.PersonRepository;
import com.helmesbackend.task.helmes.repository.SectorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final SectorRepository sectorRepository;
    private final PersonMapper personMapper;

    @Autowired
    public PersonService(PersonRepository personRepository, SectorRepository sectorRepository, PersonMapper personMapper) {
        this.personRepository = personRepository;
        this.sectorRepository = sectorRepository;
        this.personMapper = personMapper;
    }

    public PersonDTO savePerson(PersonFormDTO formDto) {
        // Create the Person entity
        Person person = new Person();
        person.setName(formDto.getName());
        person.setAgreeTerms(formDto.getAgreeTerms());
        person.setCreatedAt(LocalDateTime.now());
        person.setUpdatedAt(LocalDateTime.now());

        // Save the person first to get an ID
        person = personRepository.save(person);

        for (Long sectorId : formDto.getSectorIds()) {
            Sector sector = sectorRepository.findById(sectorId)
                    .orElseThrow(() -> new RuntimeException("Sector not found with ID: " + sectorId));

            person.addSector(sector);
        }

        person = personRepository.save(person);

        // Return a DTO
        return personMapper.toDto(person);
    }

    public PersonDTO getPersonById(Long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Person not found with ID: " + id));
        return personMapper.toDto(person);
    }
}
