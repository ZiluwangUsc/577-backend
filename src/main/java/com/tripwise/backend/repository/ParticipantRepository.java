package com.tripwise.backend.repository;

import com.tripwise.backend.entity.Trip;
import com.tripwise.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tripwise.backend.entity.Participant;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    List<Participant> findByUser(User user);

    List<Participant> findByTrip(Trip trip);


    Optional<Participant> findByUserAndTrip(User user, Trip trip);
}
