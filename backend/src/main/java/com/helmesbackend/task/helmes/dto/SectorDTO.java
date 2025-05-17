package com.helmesbackend.task.helmes.dto;

import com.helmesbackend.task.helmes.model.Sector;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SectorDTO {
    private Long id;
    private String name;
    private Integer level;
    private Long parentId;

    @Builder.Default
    private List<SectorDTO> children = new ArrayList<>();

}
