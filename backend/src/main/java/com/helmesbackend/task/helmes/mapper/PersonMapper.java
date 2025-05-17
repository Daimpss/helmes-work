package com.helmesbackend.task.helmes.mapper;

import com.helmesbackend.task.helmes.dto.PersonDTO;
import com.helmesbackend.task.helmes.dto.SectorDTO;
import com.helmesbackend.task.helmes.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PersonMapper {

    private final SectorMapper sectorMapper;

    @Autowired
    public PersonMapper(SectorMapper sectorMapper) {
        this.sectorMapper = sectorMapper;
    }


    public PersonDTO toDto(Person person) {
        if (person == null) {
            return null;
        }

        // Map sectors from PersonSector relationships
        Set<SectorDTO> sectorDtos = person.getPersonSectors().stream()
                .map(ps -> sectorMapper.toDto(ps.getSector()))
                .collect(Collectors.toSet());

        return PersonDTO.builder()
                .id(person.getId())
                .name(person.getName())
                .agreeTerms(person.getAgreeTerms())
                .sectors(sectorDtos)
                .createdAt(person.getCreatedAt())
                .updatedAt(person.getUpdatedAt())
                .build();
    }
}
