package com.tripwise.backend.service;

//import com.tripwise.backend.dto.request.travel.TripCreateRequestDto;
import com.tripwise.backend.dto.request.trip.TripCreateRequestDto;
import com.tripwise.backend.entity.Trip;

public interface ITripService {
    Trip createTravel(TripCreateRequestDto dto, Integer userId);
}
