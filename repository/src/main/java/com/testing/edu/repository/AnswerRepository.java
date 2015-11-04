package com.testing.edu.repository;

import com.testing.edu.entity.Answers;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends PagingAndSortingRepository<Answers, Long>, JpaSpecificationExecutor<Answers> {

}
