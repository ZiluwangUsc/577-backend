//package com.tripwise.backend.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import com.tripwise.backend.entity.Trip;
//import com.tripwise.backend.repository.TripRepository;
//import com.tripwise.backend.dto.request.trip.TripCreateRequestDto;
//import com.tripwise.backend.dto.response.trip.TripCreateResponseDto;
//import com.tripwise.backend.dto.response.trip.TripActivityDto;
//import com.tripwise.backend.dto.response.trip.TripListResponseDto;
//
//import java.time.LocalDate;
//import java.util.*;
//
//@RestController
//@RequestMapping("/travels")  // Context path /api + this => /api/travels
//@CrossOrigin
//public class TripController {
//
//    @Autowired
//    private TripRepository tripRepository;
//
//    // Create Trip: POST /api/travels
//    @PostMapping
//    public ResponseEntity<TripCreateResponseDto> createTrip(@RequestBody TripCreateRequestDto request) {
//        Integer userId;
//        try {
//            userId = Integer.valueOf(request.getUserId());
//        } catch (NumberFormatException e) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//
//        Trip trip = new Trip();
//        trip.setTripName(request.getName());
//        trip.setDetails(request.getDescription());
//        trip.setCreatedBy(userId);
//        if (request.getStartDate() != null) {
//            trip.setStartDate(LocalDate.parse(request.getStartDate()));
//        }
//        if (request.getEndDate() != null) {
//            trip.setEndDate(LocalDate.parse(request.getEndDate()));
//        }
//        Trip savedTrip = tripRepository.save(trip);
//        TripCreateResponseDto responseDto = new TripCreateResponseDto(201, "" + savedTrip.getTripId());
//        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
//    }
//
//    // Get All Trips for a User: GET /api/travels/{userId}
//    @GetMapping("/users/{userId}")
//    public ResponseEntity<?> getAllTripsForUser(@PathVariable Integer userId) {
//        // Fetch trips for the given userId
//        List<Trip> trips = tripRepository.findByCreatedBy(userId);
//
//        // If no trips are found, return a custom error response
//        if (trips.isEmpty()) {
//            Map<String, Object> errorResponse = new HashMap<>();
//            errorResponse.put("code", 404);
//            errorResponse.put("userId", userId.toString());
//            errorResponse.put("error", "User not found or has no activities");
//            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
//        }
//
//        // Otherwise, convert each Trip entity to a TripActivityDto
//        List<TripActivityDto> activityList = new ArrayList<>();
//        for (Trip trip : trips) {
//            String groupId = trip.getTripId().toString();
//            String name = trip.getTripName();
//            String description = trip.getDetails();
//            String startDate = trip.getStartDate() == null ? null : trip.getStartDate().toString();
//            String endDate = trip.getEndDate() == null ? null : trip.getEndDate().toString();
//            List<Integer> participants = trip.getParticipants();
//
//            TripActivityDto activityDto = new TripActivityDto(groupId, name, description, startDate, endDate,participants);
//            activityList.add(activityDto);
//        }
//
//        // Build the successful response DTO
//        TripListResponseDto responseDto = new TripListResponseDto(200, userId.toString(), activityList);
//        return new ResponseEntity<>(responseDto, HttpStatus.OK);
//    }
//
//}

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
import com.tripwise.backend.dto.response.trip.TripActivityDetailResponseDto;
import com.tripwise.backend.dto.response.trip.TripErrorResponseDto;
import com.tripwise.backend.dto.response.trip.TripDeleteResponseDto;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/travels")  // Combined with context path /api => /api/travels
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

        // Automatically add the creator as a participant as an Integer.
        List<Integer> participants = new ArrayList<>();
        participants.add(userId);
        trip.setParticipants(participants);

        Trip savedTrip = tripRepository.save(trip);
        // Return the tripId as a plain string.
        TripCreateResponseDto responseDto = new TripCreateResponseDto(201, savedTrip.getTripId().toString());
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // Get All Trips for a User: GET /api/travels/users/{userId}
    // Now using a repository method that finds trips where the user is a participant.
    @GetMapping("/users/{userId}")
    public ResponseEntity<?> getAllTripsForUser(@PathVariable Integer userId) {
        // Use the new repository method (findByParticipant) which should be defined to accept an Integer.
        List<Trip> trips = tripRepository.findByParticipant(userId);
        if (trips.isEmpty()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("code", 404);
            errorResponse.put("userId", userId.toString());
            errorResponse.put("error", "User not found or has no activities");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        List<TripActivityDto> activityList = new ArrayList<>();
        for (Trip trip : trips) {
            Integer groupId = trip.getTripId();  // Plain numeric ID as string.
            String name = trip.getTripName();
            String description = trip.getDetails();
            String startDate = trip.getStartDate() == null ? null : trip.getStartDate().toString();
            String endDate = trip.getEndDate() == null ? null : trip.getEndDate().toString();
            // Now participants is a List<Integer>.
            List<Integer> participants = trip.getParticipants();

            // Assuming your TripActivityDto is updated to hold List<Integer> for participants.
            TripActivityDto activityDto = new TripActivityDto(
                    groupId,
                    name,
                    description,
                    startDate,
                    endDate,
                    participants);
            activityList.add(activityDto);
        }

        TripListResponseDto responseDto = new TripListResponseDto(200, userId, activityList);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // Get a Single Activity by ID (and join it)
    // Endpoint: GET /api/travels/activities/{activityId}?userId=789
    // Here activityId is expected to be a plain numeric string.
    @GetMapping("/activities/{activityId}")
    public ResponseEntity<?> getActivityById(@PathVariable String activityId,
                                             @RequestParam String userId) {
        Integer tripId;
        try {
            tripId = Integer.valueOf(activityId);
        } catch (NumberFormatException e) {
            TripErrorResponseDto errorDto = new TripErrorResponseDto(404, "Activity not found");
            return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
        }

        Optional<Trip> optionalTrip = tripRepository.findById(tripId);
        if (!optionalTrip.isPresent()) {
            TripErrorResponseDto errorDto = new TripErrorResponseDto(404, "Activity not found");
            return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
        }

        Trip trip = optionalTrip.get();

        // Parse the userId parameter to an integer.
        Integer uid;
        try {
            uid = Integer.valueOf(userId);
        } catch (NumberFormatException e) {
            TripErrorResponseDto errorDto = new TripErrorResponseDto(400, "Invalid userId");
            return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
        }

        // Add the user to the activity's participants if not already present.
        if (trip.getParticipants() == null) {
            trip.setParticipants(new ArrayList<>());
        }
        if (!trip.getParticipants().contains(uid)) {
            trip.getParticipants().add(uid);
            tripRepository.save(trip);
        }

        TripActivityDto activityDto = new TripActivityDto(
                trip.getTripId(),
                trip.getTripName(),
                trip.getDetails(),
                trip.getStartDate() == null ? null : trip.getStartDate().toString(),
                trip.getEndDate() == null ? null : trip.getEndDate().toString(),
                trip.getParticipants()
        );

        TripActivityDetailResponseDto responseDto = new TripActivityDetailResponseDto(200, activityDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // Delete a user from Activity by userId & activityId
    // Final endpoint: DELETE /api/travels/activities/{activityId}/users/{userId}
    @DeleteMapping("/activities/{activityId}/users/{userId}")
    public ResponseEntity<?> removeUserFromActivity(@PathVariable String activityId,
                                                    @PathVariable String userId) {
        Integer tripId;
        try {
            tripId = Integer.valueOf(activityId);
        } catch (NumberFormatException e) {
            TripErrorResponseDto errorDto = new TripErrorResponseDto(404, "Activity not found");
            return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
        }

        Optional<Trip> optionalTrip = tripRepository.findById(tripId);
        if (!optionalTrip.isPresent()) {
            TripErrorResponseDto errorDto = new TripErrorResponseDto(404, "Activity not found");
            return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
        }
        Trip trip = optionalTrip.get();

        // Convert userId to an integer.
        Integer uid;
        try {
            uid = Integer.valueOf(userId);
        } catch (NumberFormatException e) {
            TripErrorResponseDto errorDto = new TripErrorResponseDto(400, "Invalid userId");
            return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
        }

        if (trip.getParticipants() == null || !trip.getParticipants().contains(uid)) {
            TripErrorResponseDto errorDto = new TripErrorResponseDto(404, "User is not in the activity");
            return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
        }

        trip.getParticipants().remove(uid);
        tripRepository.save(trip);

        TripDeleteResponseDto responseDto = new TripDeleteResponseDto(200);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}

