package com.testing.edu.repository;

import com.testing.edu.entity.Answers;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends CrudRepository<Answers, Long> {

}
