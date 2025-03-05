

package com.tripwise.backend.controller;

import com.tripwise.backend.entity.Participant;
import com.tripwise.backend.entity.User;
import com.tripwise.backend.repository.ParticipantRepository;
import com.tripwise.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tripwise.backend.entity.Trip;
import com.tripwise.backend.repository.TripRepository;
import com.tripwise.backend.dto.request.travel.TripCreateRequestDto;
import com.tripwise.backend.dto.response.travel.TripCreateResponseDto;
import com.tripwise.backend.dto.response.travel.TripActivityDto;
import com.tripwise.backend.dto.response.travel.TripListResponseDto;
import com.tripwise.backend.dto.response.travel.TripActivityDetailResponseDto;
import com.tripwise.backend.dto.response.travel.TripErrorResponseDto;
import com.tripwise.backend.dto.response.travel.TripDeleteResponseDto;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/travels")  // Combined with context path /api => /api/travels
@CrossOrigin
public class TripController {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    // Create Trip: POST /api/travels
    @PostMapping
    public ResponseEntity<?> createTrip(@RequestBody TripCreateRequestDto request) {
        Integer userId;
        try {
            userId = Integer.valueOf(request.getUserId());
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Create the Trip entity
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

        // Save the Trip entity (note: the 'participants' field has been removed from Trip)
        Trip savedTrip = tripRepository.save(trip);

        // Create a new Participant entity to link the user and the trip
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            Participant participant = new Participant(userOpt.get(), savedTrip);
            participantRepository.save(participant);
        } else {
            // Optionally handle the case when the user does not exist.
            TripErrorResponseDto errorDto = new TripErrorResponseDto(404, "User not found");
            return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
        }

        TripCreateResponseDto responseDto = new TripCreateResponseDto(201, savedTrip.getTripId());
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<?> getAllTripsForUser(@PathVariable Integer userId) {
        // Retrieve the User first
        Optional<User> userOpt = userRepository.findById(userId);
        if (!userOpt.isPresent()) {
            // Return an error DTO if the user is not found
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("code", 404);
            errorResponse.put("userId", userId);
            errorResponse.put("error", "User not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        User user = userOpt.get();
        // Retrieve all Participant records for this user (i.e., all trips the user belongs to)
        List<Participant> participantRecords = participantRepository.findByUser(user);
        if (participantRecords.isEmpty()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("code", 404);
            errorResponse.put("userId", userId);
            errorResponse.put("error", "User has no activities");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        List<TripActivityDto> activityList = new ArrayList<>();
        for (Participant participant : participantRecords) {
            Trip trip = participant.getTrip();
            if (trip != null) {
                Integer groupId = trip.getTripId();
                String name = trip.getTripName();
                String description = trip.getDetails();
                String startDate = (trip.getStartDate() == null) ? null : trip.getStartDate().toString();
                String endDate = (trip.getEndDate() == null) ? null : trip.getEndDate().toString();

                // Now, retrieve all Participant records for this trip to populate the list of user IDs.
                List<Participant> participantsForTrip = participantRepository.findByTrip(trip);
                List<Integer> tripParticipants = new ArrayList<>();
                for (Participant p : participantsForTrip) {
                    // Assuming that p.getUser() is not null
                    tripParticipants.add(p.getUser().getUserId());
                }

                TripActivityDto activityDto = new TripActivityDto(
                        groupId,
                        name,
                        description,
                        startDate,
                        endDate,
                        tripParticipants
                );
                activityList.add(activityDto);
            }
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
        // Convert activityId (expected as a plain numeric string) to tripId
        Integer tripId;
        try {
            tripId = Integer.valueOf(activityId);
        } catch (NumberFormatException e) {
            TripErrorResponseDto errorDto = new TripErrorResponseDto(404, "Activity not found");
            return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
        }

        // Retrieve the Trip entity
        Optional<Trip> optionalTrip = tripRepository.findById(tripId);
        if (!optionalTrip.isPresent()) {
            TripErrorResponseDto errorDto = new TripErrorResponseDto(404, "Activity not found");
            return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
        }
        Trip trip = optionalTrip.get();

        // Parse and retrieve the User entity
        Integer uid;
        try {
            uid = Integer.valueOf(userId);
        } catch (NumberFormatException e) {
            TripErrorResponseDto errorDto = new TripErrorResponseDto(400, "Invalid userId");
            return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
        }
        Optional<User> optionalUser = userRepository.findById(uid);
        if (!optionalUser.isPresent()) {
            TripErrorResponseDto errorDto = new TripErrorResponseDto(404, "User not found");
            return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
        }
        User user = optionalUser.get();

        // Check if the user is already a participant for this trip.
        Optional<Participant> participantOpt = participantRepository.findByUserAndTrip(user, trip);
        if (!participantOpt.isPresent()) {
            // Create a new Participant record linking the user and the trip.
            Participant newParticipant = new Participant(user, trip);
            participantRepository.save(newParticipant);
        }

        // Retrieve all Participant records for this trip.
        List<Participant> participantsForTrip = participantRepository.findByTrip(trip);
        List<Integer> participantIds = new ArrayList<>();
        for (Participant p : participantsForTrip) {
            // Assuming p.getUser() returns the User entity
            participantIds.add(p.getUser().getUserId());
        }

        // Build the activity DTO
        TripActivityDto activityDto = new TripActivityDto(
                trip.getTripId(),
                trip.getTripName(),
                trip.getDetails(),
                (trip.getStartDate() == null ? null : trip.getStartDate().toString()),
                (trip.getEndDate() == null ? null : trip.getEndDate().toString()),
                participantIds
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

        Integer uid;
        try {
            uid = Integer.valueOf(userId);
        } catch (NumberFormatException e) {
            TripErrorResponseDto errorDto = new TripErrorResponseDto(400, "Invalid userId");
            return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
        }

        Optional<User> optionalUser = userRepository.findById(uid);
        if (!optionalUser.isPresent()) {
            TripErrorResponseDto errorDto = new TripErrorResponseDto(404, "User not found");
            return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
        }
        User user = optionalUser.get();

        // Look for a Participant record linking the user and the trip.
        Optional<Participant> participantOpt = participantRepository.findByUserAndTrip(user, trip);
        if (!participantOpt.isPresent()) {
            TripErrorResponseDto errorDto = new TripErrorResponseDto(404, "User is not in the activity");
            return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
        }

        // Delete the Participant record.
        participantRepository.delete(participantOpt.get());

        TripDeleteResponseDto responseDto = new TripDeleteResponseDto(200);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

}

