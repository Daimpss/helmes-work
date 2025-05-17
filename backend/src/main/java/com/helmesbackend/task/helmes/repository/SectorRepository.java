package com.helmesbackend.task.helmes.repository;

import com.helmesbackend.task.helmes.model.Sector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectorRepository extends JpaRepository<Sector, Long> {
    List<Sector> findByParentIsNullOrderByName();
    List<Sector> findByLevelOrderByName(Integer level);
}
