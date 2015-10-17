package com.testing.edu.repository;

import com.testing.edu.entity.Students;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentsRepository extends CrudRepository<Students, Long> {

}
