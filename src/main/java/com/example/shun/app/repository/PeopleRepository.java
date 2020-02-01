package com.example.shun.app.repository;

import com.example.shun.app.model.People;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PeopleRepository extends JpaRepository<People, Long> {

    List<People> findByFirst(String firstName);

    List<People> findByLast(String lastName);

    @Query(value = "select * from people p "+
                   "where p.first like %:name% "+
                   "or p.last like %:name%",
           nativeQuery = true)
    List<People> findByFirstOrLastLike(@Param("name") String name);
}
