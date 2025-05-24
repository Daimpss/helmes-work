package com.helmesbackend.task.helmes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.helmesbackend.task.helmes.dto.SectorDTO;
import com.helmesbackend.task.helmes.mapper.SectorMapper;
import com.helmesbackend.task.helmes.model.Sector;
import com.helmesbackend.task.helmes.service.SectorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class SectorControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SectorService sectorService;

    @Mock
    private SectorMapper sectorMapper;

    @InjectMocks
    private SectorController sectorController;

    private ObjectMapper objectMapper;
    private List<Sector> mockSectors;
    private List<SectorDTO> mockSectorDTOs;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(sectorController).build();
        objectMapper = new ObjectMapper();

        Sector manufacturingSector = new Sector();
        manufacturingSector.setId(1L);
        manufacturingSector.setName("Manufacturing");
        manufacturingSector.setLevel(0);
        manufacturingSector.setParent(null);

        Sector foodSector = new Sector();
        foodSector.setId(6L);
        foodSector.setName("Food and Beverage");
        foodSector.setLevel(1);
        foodSector.setParent(manufacturingSector);

        Sector bakerySector = new Sector();
        bakerySector.setId(342L);
        bakerySector.setName("Bakery & confectionery products");
        bakerySector.setLevel(2);
        bakerySector.setParent(foodSector);

        mockSectors = Arrays.asList(manufacturingSector, foodSector, bakerySector);

        SectorDTO manufacturingDTO = new SectorDTO();
        manufacturingDTO.setId(1L);
        manufacturingDTO.setName("Manufacturing");
        manufacturingDTO.setLevel(0);
        manufacturingDTO.setParentId(null);

        SectorDTO foodDTO = new SectorDTO();
        foodDTO.setId(6L);
        foodDTO.setName("Food and Beverage");
        foodDTO.setLevel(1);
        foodDTO.setParentId(1L);

        SectorDTO bakeryDTO = new SectorDTO();
        bakeryDTO.setId(342L);
        bakeryDTO.setName("Bakery & confectionery products");
        bakeryDTO.setLevel(2);
        bakeryDTO.setParentId(6L);

        mockSectorDTOs = Arrays.asList(manufacturingDTO, foodDTO, bakeryDTO);
    }

    @Test
    void getAllSectors_ShouldReturnListOfSectorDTOs() throws Exception {
        // Arrange
        when(sectorService.getAllSectors()).thenReturn(mockSectors);
        when(sectorMapper.toHierarchicalDtoList(mockSectors)).thenReturn(mockSectorDTOs);

        // Act & Assert
        mockMvc.perform(get("/api/sectors")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Manufacturing"))
                .andExpect(jsonPath("$[0].level").value(0))
                .andExpect(jsonPath("$[0].parentId").isEmpty())
                .andExpect(jsonPath("$[1].id").value(6))
                .andExpect(jsonPath("$[1].name").value("Food and Beverage"))
                .andExpect(jsonPath("$[1].level").value(1))
                .andExpect(jsonPath("$[1].parentId").value(1))
                .andExpect(jsonPath("$[2].id").value(342))
                .andExpect(jsonPath("$[2].name").value("Bakery & confectionery products"))
                .andExpect(jsonPath("$[2].level").value(2))
                .andExpect(jsonPath("$[2].parentId").value(6));
    }

    @Test
    void getAllSectors_WithEmptyList_ShouldReturnEmptyArray() throws Exception {
        // Arrange
        when(sectorService.getAllSectors()).thenReturn(Arrays.asList());
        when(sectorMapper.toHierarchicalDtoList(Arrays.asList())).thenReturn(Arrays.asList());

        // Act & Assert
        mockMvc.perform(get("/api/sectors")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }
}