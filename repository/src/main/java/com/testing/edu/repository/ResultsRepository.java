package com.testing.edu.repository;

import com.testing.edu.entity.Result;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultsRepository extends CrudRepository<Result, Long> {

}
