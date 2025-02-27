package com.tripwise.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tripwise.backend.entity.Trip;
import com.tripwise.backend.repository.TripRepository;
import com.tripwise.backend.dto.request.trip.TripCreateRequestDto;
import com.tripwise.backend.dto.response.trip.TripCreateResponseDto;
import com.tripwise.backend.dto.response.trip.TripActivityDto;
import com.tripwise.backend.dto.response.trip.TripListResponseDto;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/travels")  // Context path /api + this => /api/travels
@CrossOrigin
public class TripController {

    @Autowired
    private TripRepository tripRepository;

    // Create Trip: POST /api/travels
    @PostMapping
    public ResponseEntity<TripCreateResponseDto> createTrip(@RequestBody TripCreateRequestDto request) {
        Integer userId;
        try {
            userId = Integer.valueOf(request.getUserId());
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Trip trip = new Trip();
        trip.setTripName(request.getName());
        trip.setDetails(request.getDescription());
        trip.setCreatedBy(userId);
        if (request.getStartDate() != null) {
            trip.setStartDate(LocalDate.parse(request.getStartDate()));
        }
        if (request.getEndDate() != null) {
            trip.setEndDate(LocalDate.parse(request.getEndDate()));
        }
        Trip savedTrip = tripRepository.save(trip);
        TripCreateResponseDto responseDto = new TripCreateResponseDto(201, "" + savedTrip.getTripId());
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // Get All Trips for a User: GET /api/travels/{userId}
    @GetMapping("/users/{userId}")
    public ResponseEntity<?> getAllTripsForUser(@PathVariable Integer userId) {
        // Fetch trips for the given userId
        List<Trip> trips = tripRepository.findByCreatedBy(userId);

        // If no trips are found, return a custom error response
        if (trips.isEmpty()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("code", 404);
            errorResponse.put("userId", userId.toString());
            errorResponse.put("error", "User not found or has no activities");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        // Otherwise, convert each Trip entity to a TripActivityDto
        List<TripActivityDto> activityList = new ArrayList<>();
        for (Trip trip : trips) {
            String groupId = trip.getTripId().toString();
            String name = trip.getTripName();
            String description = trip.getDetails();
            String startDate = trip.getStartDate() == null ? null : trip.getStartDate().toString();
            String endDate = trip.getEndDate() == null ? null : trip.getEndDate().toString();

            TripActivityDto activityDto = new TripActivityDto(groupId, name, description, startDate, endDate);
            activityList.add(activityDto);
        }

        // Build the successful response DTO
        TripListResponseDto responseDto = new TripListResponseDto(200, userId.toString(), activityList);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

}
