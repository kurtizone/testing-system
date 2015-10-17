package com.testing.edu.repository;

import com.testing.edu.entity.Tests;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestsRepository extends CrudRepository<Tests, Long> {

}
