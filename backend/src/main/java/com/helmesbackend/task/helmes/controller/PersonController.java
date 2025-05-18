package com.helmesbackend.task.helmes.controller;

import com.helmesbackend.task.helmes.dto.ErrorResponseDTO;
import com.helmesbackend.task.helmes.dto.PersonDTO;
import com.helmesbackend.task.helmes.dto.PersonFormDTO;
import com.helmesbackend.task.helmes.dto.SessionResultDTO;
import com.helmesbackend.task.helmes.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
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
    public ResponseEntity<PersonDTO> savePerson(@Valid @RequestBody PersonFormDTO formDto,
                                                @CookieValue(name = "session_token", required = false) String token) {

        log.info("Received request to save person: name={}, sectors count={}, agreeTerms={}",
                formDto.getName(), formDto.getSectorIds().size(), formDto.getAgreeTerms());

        SessionResultDTO result = personService.processPersonForm(formDto, token);
        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.ok();

        if (result.isNewSession() || token == null) {
            // Create cookie with the token (7 days expiration)
            ResponseCookie cookie = ResponseCookie.from("session_token", result.getToken())
                    .httpOnly(true)
                    .path("/")
                    .maxAge(7 * 24 * 60 * 60) // 7 days in seconds
                    .build();

            responseBuilder.header(HttpHeaders.SET_COOKIE, cookie.toString());
        }
        return responseBuilder.body(result.getPerson());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get person by ID",
            description = "Retrieves a person by their ID including selected sectors")
    public ResponseEntity<PersonDTO> getPersonById(@PathVariable Long id) {
        return ResponseEntity.ok(personService.getPersonById(id));
    }

    @GetMapping("/current")
    @Operation(summary = "Get current person", description = "Get person data for current session")
    public ResponseEntity<?> getCurrentPerson(
            @CookieValue(name = "session_token", required = false) String token) {

        log.debug("Received request to get current person");

        PersonDTO person = personService.getCurrentPerson(token);

        if (person != null) {
            return ResponseEntity.ok(person);
        } else {
            ErrorResponseDTO errorResponse = ErrorResponseDTO.of(
                    HttpStatus.NOT_FOUND.value(),
                    "No active session found. Please submit the form first."
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @PostMapping("/clear")
    @Operation(summary = "Clear current session", description = "Clears the current user session to allow a new user")
    public ResponseEntity<Map<String, String>> clearSession(
            @CookieValue(name = "session_token", required = false) String currentToken) {

        if (currentToken != null) {
            PersonDTO currentPerson = personService.getCurrentPerson(currentToken);
            if (currentPerson != null) {
                log.info("Clearing session for user: {}", currentPerson.getName());
            } else {
                log.info("Clearing invalid/expired session");
            }
        } else {
            log.info("Clear session requested, but no active session found");
        }

        ResponseCookie cookie = ResponseCookie.from("session_token", "")
                .httpOnly(true)
                .path("/")
                .maxAge(0)
                .build();

        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", "Session cleared successfully. You can now enter new user data.");

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(responseBody);
    }
}
