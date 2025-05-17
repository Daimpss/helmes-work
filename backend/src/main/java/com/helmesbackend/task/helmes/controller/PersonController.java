package com.helmesbackend.task.helmes.controller;

import com.helmesbackend.task.helmes.dto.PersonDTO;
import com.helmesbackend.task.helmes.dto.PersonFormDTO;
import com.helmesbackend.task.helmes.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/persons")
@Tag(name = "Persons", description = "Endpoints for managing persons")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }


    @PostMapping
    @Operation(summary = "Save person with sectors",
            description = "Saves a person along with their selected sectors")
    public ResponseEntity<PersonDTO> savePerson(@Valid @RequestBody PersonFormDTO formDto) {
        PersonDTO savedPerson = personService.savePerson(formDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedPerson);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get person by ID",
            description = "Retrieves a person by their ID including selected sectors")
    public ResponseEntity<PersonDTO> getPersonById(@PathVariable Long id) {
        return ResponseEntity.ok(personService.getPersonById(id));
    }
}
