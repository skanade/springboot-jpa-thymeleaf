package com.example.shun.app.repository;

import com.example.shun.app.model.People;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PeopleRepository extends JpaRepository<People, Long> {

    List<People> findByFirst(String firstName);

    List<People> findByLast(String lastName);
}
