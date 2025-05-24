package com.helmesbackend.task.helmes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.helmesbackend.task.helmes.dto.PersonDTO;
import com.helmesbackend.task.helmes.dto.PersonFormDTO;
import com.helmesbackend.task.helmes.dto.SessionResultDTO;
import com.helmesbackend.task.helmes.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PersonControllerTest {


    private MockMvc mockMvc;

    @Mock
    private PersonService personService;

    @InjectMocks
    private PersonController personController;

    private ObjectMapper objectMapper;

    private PersonFormDTO validFormDto;
    private PersonDTO mockPersonDto;
    private SessionResultDTO mockSessionResult;

    @BeforeEach
    void setUp() {
        // Setup test data
        mockMvc = MockMvcBuilders.standaloneSetup(personController).build();
        objectMapper = new ObjectMapper();
        Set<Long> sectorIds = Set.of(1L, 2L, 3L);

        validFormDto = new PersonFormDTO();
        validFormDto.setName("John Doe");
        validFormDto.setSectorIds(sectorIds);
        validFormDto.setAgreeTerms(true);

        mockPersonDto = new PersonDTO();
        mockPersonDto.setId(1L);
        mockPersonDto.setName("John Doe");
        mockPersonDto.setAgreeTerms(true);
        // Set sectors if needed

        mockSessionResult = new SessionResultDTO();
        mockSessionResult.setPerson(mockPersonDto);
        mockSessionResult.setToken("mock-jwt-token");
        mockSessionResult.setNewSession(true);
    }

    @Test
    void savePerson_WithValidData_ShouldReturnPersonAndSetCookie() throws Exception {
        // Arrange
        when(personService.processPersonForm(any(PersonFormDTO.class), eq(null)))
                .thenReturn(mockSessionResult);

        // Act & Assert
        mockMvc.perform(post("/api/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validFormDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.agreeTerms").value(true))
                .andExpect(header().exists("Set-Cookie"))
                .andExpect(header().string("Set-Cookie",
                        org.hamcrest.Matchers.containsString("session_token=mock-jwt-token")))
                .andExpect(header().string("Set-Cookie",
                        org.hamcrest.Matchers.containsString("HttpOnly")));
    }

    @Test
    void savePerson_WithExistingSession_ShouldReturnPersonWithoutSettingNewCookie() throws Exception {
        // Arrange
        mockSessionResult.setNewSession(false); // Existing session
        when(personService.processPersonForm(any(PersonFormDTO.class), eq("existing-token")))
                .thenReturn(mockSessionResult);

        // Act & Assert
        mockMvc.perform(post("/api/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validFormDto))
                        .cookie(new jakarta.servlet.http.Cookie("session_token", "existing-token")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(header().doesNotExist("Set-Cookie"));
    }

    @Test
    void savePerson_WithInvalidData_ShouldReturnBadRequest() throws Exception {
        // Arrange - Create invalid form data
        PersonFormDTO invalidForm = new PersonFormDTO();
        invalidForm.setName(""); // Invalid: empty name
        invalidForm.setSectorIds(Set.of());
        invalidForm.setAgreeTerms(false);

        // Act & Assert
        mockMvc.perform(post("/api/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidForm)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getCurrentPerson_WithValidSession_ShouldReturnPerson() throws Exception {
        // Arrange
        when(personService.getCurrentPerson("valid-token")).thenReturn(mockPersonDto);

        // Act & Assert
        mockMvc.perform(get("/api/persons")
                        .cookie(new jakarta.servlet.http.Cookie("session_token", "valid-token")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.agreeTerms").value(true));
    }

    @Test
    void getCurrentPerson_WithInvalidSession_ShouldReturnNotFound() throws Exception {
        // Arrange
        when(personService.getCurrentPerson("invalid-token")).thenReturn(null);

        // Act & Assert
        mockMvc.perform(get("/api/persons")
                        .cookie(new jakarta.servlet.http.Cookie("session_token", "invalid-token")))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("No active session found. Please submit the form first."));
    }

    @Test
    void getCurrentPerson_WithoutSession_ShouldReturnNotFound() throws Exception {
        // Arrange
        when(personService.getCurrentPerson(null)).thenReturn(null);

        // Act & Assert
        mockMvc.perform(get("/api/persons"))
                .andExpect(status().isNotFound());
    }

    @Test
    void clearSession_WithValidSession_ShouldClearCookieAndReturnSuccess() throws Exception {
        // Arrange
        when(personService.getCurrentPerson("valid-token")).thenReturn(mockPersonDto);

        // Act & Assert
        mockMvc.perform(post("/api/persons/clear")
                        .cookie(new jakarta.servlet.http.Cookie("session_token", "valid-token")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Session cleared successfully. You can now enter new user data."))
                .andExpect(header().exists("Set-Cookie"))
                .andExpect(header().string("Set-Cookie",
                        org.hamcrest.Matchers.containsString("session_token=")))
                .andExpect(header().string("Set-Cookie",
                        org.hamcrest.Matchers.containsString("Max-Age=0")));
    }

    @Test
    void clearSession_WithoutSession_ShouldStillReturnSuccess() throws Exception {

        // Act & Assert
        mockMvc.perform(post("/api/persons/clear"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Session cleared successfully. You can now enter new user data."))
                .andExpect(header().exists("Set-Cookie"));
    }
}