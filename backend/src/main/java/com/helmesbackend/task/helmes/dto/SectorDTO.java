package com.helmesbackend.task.helmes.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
