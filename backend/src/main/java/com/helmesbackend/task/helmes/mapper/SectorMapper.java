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


    public List<SectorDTO> toHierarchicalDtoList(List<Sector> allSectors) {
        List<SectorDTO> allDtos = allSectors.stream()
                .map(this::toDto)
                .toList();

        Map<Long, SectorDTO> dtoMap = allDtos.stream()
                .collect(Collectors.toMap(SectorDTO::getId, dto -> dto));

        List<SectorDTO> rootDtos = new ArrayList<>();

        for (SectorDTO dto : allDtos) {
            if (dto.getParentId() == null) {
                rootDtos.add(dto);
            } else {
                SectorDTO parent = dtoMap.get(dto.getParentId());
                if (parent != null) {
                    parent.getChildren().add(dto);
                }
            }
        }
        return rootDtos;
    }
}
