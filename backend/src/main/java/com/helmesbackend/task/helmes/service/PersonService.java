package com.helmesbackend.task.helmes.service;

import com.helmesbackend.task.helmes.dto.PersonDTO;
import com.helmesbackend.task.helmes.dto.PersonFormDTO;
import com.helmesbackend.task.helmes.dto.SessionResultDTO;
import com.helmesbackend.task.helmes.mapper.PersonMapper;
import com.helmesbackend.task.helmes.model.Person;
import com.helmesbackend.task.helmes.model.Sector;
import com.helmesbackend.task.helmes.repository.PersonRepository;
import com.helmesbackend.task.helmes.repository.SectorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Slf4j
@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final SectorRepository sectorRepository;
    private final PersonMapper personMapper;
    private final JwtService jwtService;

    @Autowired
    public PersonService(PersonRepository personRepository, SectorRepository sectorRepository, PersonMapper personMapper, JwtService jwtService) {
        this.personRepository = personRepository;
        this.sectorRepository = sectorRepository;
        this.personMapper = personMapper;
        this.jwtService = jwtService;
    }


    public SessionResultDTO processPersonForm(PersonFormDTO formDto, String token) {
        Long personId = null;

        if (token != null && jwtService.validateToken(token)) {
            personId = jwtService.extractPersonId(token);
            log.debug("Found existing person ID from token: {}", personId);
        }
        else {
            log.debug("No valid token provided, will create new person");
        }

        PersonDTO personDTO;
        String newToken;
        boolean isNewSession = false;

        try {
            if (personId != null) {
                log.info("Updating existing person with ID: {}", personId);
                personDTO = this.updatePerson(personId, formDto);
                newToken = token;
            } else {

                log.info("Creating new person: {}", formDto.getName());
                personDTO = this.savePerson(formDto);

                newToken = jwtService.generateToken(personDTO.getId());
                isNewSession = true;
                log.info("Created new person with ID: {}, generated new session", personDTO.getId());
            }
        } catch (Exception e) {
            log.error("Failed to process person form for: {}", formDto.getName(), e);
            throw e;
        }


        return new SessionResultDTO(personDTO, newToken, isNewSession);
    }

    public PersonDTO savePerson(PersonFormDTO formDto) {
        log.debug("Saving new person: {}", formDto.getName());

        try {
            Person person = new Person();
            person.setName(formDto.getName());
            person.setAgreeTerms(formDto.getAgreeTerms());
            person.setCreatedAt(LocalDateTime.now());
            person.setUpdatedAt(LocalDateTime.now());

            person = personRepository.save(person);

            log.debug("Adding {} sectors to person ID: {}", formDto.getSectorIds().size(), person.getId());
            addSectorsToPerson(person, formDto.getSectorIds());

            person = personRepository.save(person);
            log.info("Successfully saved person: {} with {} sectors", person.getName(), formDto.getSectorIds().size());

            return personMapper.toDto(person);
        } catch (Exception e) {
            log.error("Failed to save person: {}", formDto.getName(), e);
            throw e;
        }

    }

    public PersonDTO updatePerson(Long personId, PersonFormDTO formDto) {
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> {
                    log.warn("Person not found with ID: {}", personId);
                    return new EntityNotFoundException("Person not found with ID: " + personId);
                });
        try {
            person.setName(formDto.getName());
            person.setAgreeTerms(formDto.getAgreeTerms());
            person.setUpdatedAt(LocalDateTime.now());

            person.getPersonSectors().clear();

            addSectorsToPerson(person, formDto.getSectorIds());

            person = personRepository.save(person);

            return personMapper.toDto(person);
        } catch (Exception e) {
            log.error("Failed to update person: {}", formDto.getName(), e);
            throw e;
        }
    }

    public PersonDTO getPersonById(Long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Person not found with ID: {}", id);
                    return new EntityNotFoundException("Person not found with ID: " + id);
                });
        return personMapper.toDto(person);
    }

    public PersonDTO getCurrentPerson(String token) {
        if (token == null || !jwtService.validateToken(token)) {
            return null;
        }

        Long personId = jwtService.extractPersonId(token);
        return this.getPersonById(personId);
    }

    private void addSectorsToPerson(Person person, Set<Long> sectorIds) {
        sectorIds.forEach(sectorId -> {
            Sector sector = sectorRepository.findById(sectorId)
                    .orElseThrow(() -> new EntityNotFoundException("Sector not found with ID: " + sectorId));
            person.addSector(sector);
        });
    }
}
