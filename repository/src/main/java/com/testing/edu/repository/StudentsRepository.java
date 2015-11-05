package com.testing.edu.repository;

import com.testing.edu.entity.Students;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentsRepository extends PagingAndSortingRepository<Students, Long>, JpaSpecificationExecutor<Students> {

    @Query("Select S FROM Students S INNER JOIN S.user U WHERE U.id =:user_id")
    Students findByUserId (@Param("user_id") Long userId);
}
