package com.helmesbackend.task.helmes.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true, exclude = {"personSectors"})
@ToString(exclude = {"personSectors"})
@Entity
@Table(name = "persons")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Person extends BaseEntity {

    @NonNull
    @Column(nullable = false)
    private String name;

    @NonNull
    @Column(nullable = false)
    private Boolean agreeTerms = false;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PersonSector> personSectors = new HashSet<>();

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    public void addSector(Sector sector) {
        PersonSector personSector = new PersonSector();
        personSector.setPerson(this);
        personSector.setSector(sector);
        this.personSectors.add(personSector);
    }
}
