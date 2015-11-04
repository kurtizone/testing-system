package com.testing.edu.repository;

import com.testing.edu.entity.Questions;
import com.testing.edu.entity.Result;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultsRepository extends PagingAndSortingRepository<Result, Long>, JpaSpecificationExecutor<Result> {

}
