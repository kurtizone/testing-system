package com.testing.edu.repository;

import com.testing.edu.entity.Lecturers;
import com.testing.edu.entity.Subject;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends PagingAndSortingRepository<Subject, Long>, JpaSpecificationExecutor<Subject> {

}
