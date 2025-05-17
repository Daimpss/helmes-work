package com.helmesbackend.task.helmes.model;

import jakarta.persistence.*;
import lombok.*;

@ToString(exclude = {"person", "sector"})
@Entity
@Table(name = "person_sectors")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PersonSector extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sector_id", nullable = false)
    private Sector sector;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersonSector that = (PersonSector) o;

        return sector != null && that.sector != null &&
                sector.getId() != null && that.sector.getId() != null &&
                sector.getId().equals(that.sector.getId());
    }

    @Override
    public int hashCode() {
        return sector != null && sector.getId() != null ? sector.getId().hashCode() : 0;
    }
}
