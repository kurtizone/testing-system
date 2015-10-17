package com.testing.edu.repository;

import com.testing.edu.entity.Lecturers;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LecturersRepository extends CrudRepository<Lecturers, Long> {

}
