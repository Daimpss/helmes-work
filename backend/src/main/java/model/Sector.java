package model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sectors")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Sector extends BaseEntity {


    @Column
    @NonNull
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Sector parent;


    @OneToMany(mappedBy = "sector")
    private Set<PersonSector> personSectors = new HashSet<>();


}
