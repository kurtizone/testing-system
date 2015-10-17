package com.testing.edu.repository;

import com.testing.edu.entity.Questions;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionsRepository extends CrudRepository<Questions, Long> {

}
