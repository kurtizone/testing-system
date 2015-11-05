package com.testing.edu.repository;

import com.testing.edu.entity.Lecturers;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LecturersRepository extends PagingAndSortingRepository<Lecturers, Long>, JpaSpecificationExecutor<Lecturers> {

    @Query("Select L FROM Lecturers L INNER JOIN L.user U WHERE U.id =:user_id")
    Lecturers findByUserId (@Param("user_id") Long userId);
}
