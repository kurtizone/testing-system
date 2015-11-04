package com.testing.edu.repository;

import com.testing.edu.entity.Lecturers;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LecturersRepository extends PagingAndSortingRepository<Lecturers, Long>, JpaSpecificationExecutor<Lecturers> {

}
