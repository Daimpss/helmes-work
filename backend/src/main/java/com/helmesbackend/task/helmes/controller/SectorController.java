package com.helmesbackend.task.helmes.controller;


import com.helmesbackend.task.helmes.dto.SectorDTO;
import com.helmesbackend.task.helmes.mapper.SectorMapper;
import com.helmesbackend.task.helmes.model.Sector;
import com.helmesbackend.task.helmes.service.SectorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/sectors")
@Tag(name = "Sectors", description = "Endpoints for managing sectors")
public class SectorController {

    private final SectorService sectorService;
    private final SectorMapper sectorMapper;

    @Autowired
    public SectorController(SectorService sectorService, SectorMapper sectorMapper) {
        this.sectorService = sectorService;
        this.sectorMapper = sectorMapper;
    }

    @GetMapping
    @Operation(summary = "Get all sectors", description = "Retrieves a list of all available sectors")
    public List<SectorDTO> getAllSectors() {
        List<Sector> allSectors = sectorService.getAllSectors();
        return sectorMapper.toHierarchicalDtoList(allSectors);
    }
}