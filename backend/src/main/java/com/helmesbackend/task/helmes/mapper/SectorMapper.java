package com.helmesbackend.task.helmes.mapper;

import com.helmesbackend.task.helmes.dto.SectorDTO;
import com.helmesbackend.task.helmes.model.Sector;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class SectorMapper {

    public SectorDTO toDto(Sector sector) {
        if (sector == null) {
            return null;
        }

        return SectorDTO.builder()
                .id(sector.getId())
                .name(sector.getName())
                .level(sector.getLevel())
                .parentId(sector.getParent() != null ? sector.getParent().getId() : null)
                .build();
    }

    public List<SectorDTO> toDtoList(List<Sector> sectors) {
        return sectors.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }


    public List<SectorDTO> toHierarchicalDtoList(List<Sector> allSectors) {
        // First convert all sectors to DTOs
        List<SectorDTO> allDtos = allSectors.stream()
                .map(this::toDto)
                .collect(Collectors.toList());

        // Create a map for quick lookup by ID
        Map<Long, SectorDTO> dtoMap = allDtos.stream()
                .collect(Collectors.toMap(SectorDTO::getId, dto -> dto));

        // Build the tree structure
        List<SectorDTO> rootDtos = new ArrayList<>();

        // For each DTO, add it to its parent's children list
        for (SectorDTO dto : allDtos) {
            if (dto.getParentId() == null) {
                // This is a root level sector
                rootDtos.add(dto);
            } else {
                // Add this DTO to its parent's children list
                SectorDTO parent = dtoMap.get(dto.getParentId());
                if (parent != null) {
                    parent.getChildren().add(dto);
                }
            }
        }

        // Return only the root nodes (the complete tree is accessible through the children)
        return rootDtos;
    }
}
