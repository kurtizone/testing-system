package com.testing.edu.repository;

import com.testing.edu.entity.Questions;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionsRepository extends PagingAndSortingRepository<Questions, Long>, JpaSpecificationExecutor<Questions> {

}
