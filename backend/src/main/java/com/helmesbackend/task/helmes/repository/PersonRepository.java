package com.helmesbackend.task.helmes.repository;

import com.helmesbackend.task.helmes.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}
