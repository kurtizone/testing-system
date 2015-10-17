package com.testing.edu.repository;

import com.testing.edu.entity.Groups;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupsRepository extends CrudRepository<Groups, Long> {

}
