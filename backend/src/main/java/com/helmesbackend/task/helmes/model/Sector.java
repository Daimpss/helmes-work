package com.helmesbackend.task.helmes.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true, exclude = {"parent", "children", "personSectors"})
@ToString(exclude = {"parent", "children", "personSectors"})
@Entity
@Table(name = "sectors")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Sector extends BaseEntity {


    @Column(nullable = false)
    @NonNull
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Sector parent;

    @OneToMany(mappedBy = "parent")
    private Set<Sector> children = new HashSet<>();

    @Column
    private Integer level = 0;

    @OneToMany(mappedBy = "sector")
    private Set<PersonSector> personSectors = new HashSet<>();


}
