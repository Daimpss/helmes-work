package com.helmesbackend.task.helmes.service;

import com.helmesbackend.task.helmes.model.Sector;
import com.helmesbackend.task.helmes.repository.SectorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class SectorServiceTest {

    @Mock
    private SectorRepository sectorRepository;

    @InjectMocks
    private SectorService sectorService;

    private List<Sector> mockSectors;

    @BeforeEach
    void setUp() {
        // Create some mock sectors for testing
        Sector sector1 = new Sector();
        sector1.setId(1L);
        sector1.setName("Manufacturing");
        sector1.setLevel(0);
        sector1.setParent(null);

        Sector sector2 = new Sector();
        sector2.setId(2L);
        sector2.setName("Food and Beverage");
        sector2.setLevel(1);
        sector2.setParent(sector1);

        mockSectors = Arrays.asList(sector1, sector2);
    }

    @Test
    void getAllSectors_ShouldReturnAllSectors() {
        // Arrange
        when(sectorRepository.findAll()).thenReturn(mockSectors);

        // Act
        List<Sector> result = sectorService.getAllSectors();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Manufacturing", result.get(0).getName());
        assertEquals("Food and Beverage", result.get(1).getName());

        // Verify that the repository method was called exactly once
        verify(sectorRepository, times(1)).findAll();
    }
}