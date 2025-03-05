package com.tripwise.backend.service;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tripwise.backend.dto.request.travel.TripCreateRequestDto;
import com.tripwise.backend.entity.Trip;
import com.tripwise.backend.repository.TripRepository;

@Service
public class TripService implements ITripService {

    @Autowired
    private TripRepository tripRepository;

    @Override
    public Trip createTravel(TripCreateRequestDto dto, Integer userId) {
        Trip trip = new Trip();
        trip.setTripName(dto.getName());
        trip.setDetails(dto.getDescription());

        // Parse start/end dates from string if needed:
        if (dto.getStartDate() != null) {
            trip.setStartDate(LocalDate.parse(dto.getStartDate()));
        }
        if (dto.getEndDate() != null) {
            trip.setEndDate(LocalDate.parse(dto.getEndDate()));
        }

        // The "created_by" field in your Trip entity
        trip.setCreatedBy(userId);

        // Save to DB
        Trip saved = tripRepository.save(trip);
        return saved;
    }
}
