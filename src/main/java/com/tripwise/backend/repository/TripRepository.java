package com.tripwise.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.tripwise.backend.entity.Trip;
import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Integer> {
    List<Trip> findByCreatedBy(Integer createdBy);

//    @Query("SELECT DISTINCT t FROM Trip t JOIN t.participants p WHERE p = :userId")
//    List<Trip> findByParticipant(@Param("userId") Integer userId);

//    @Query("SELECT t FROM Trip t WHERE :userId member of t.participants")
//    List<Trip> findByParticipant(@Param("userId") Integer userId);


}
