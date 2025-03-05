package com.tripwise.backend.controller;


import com.tripwise.backend.dto.request.travel.ItineraryCreateRequestDto;
import com.tripwise.backend.dto.request.travel.ItineraryUpdateRequestDto;
import com.tripwise.backend.dto.response.travel.ItineraryCreateResponseDto;
import com.tripwise.backend.dto.response.travel.ItineraryResponseDto;
import com.tripwise.backend.dto.response.travel.ItineraryUpdateResponseDto;
import com.tripwise.backend.entity.Itinerary;
import com.tripwise.backend.entity.Trip;
import com.tripwise.backend.repository.ItineraryRepository;
import com.tripwise.backend.repository.TripRepository;
import com.tripwise.backend.service.ItineraryService;
import com.tripwise.backend.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/travels")  // Combined with context path /api => /api/travels
@CrossOrigin

public class ItineraryController {


    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private ItineraryRepository itineraryRepository;

    @Autowired
    private ItineraryService itineraryService;


    @PostMapping(path = "{travelId}/itineraries")
    public ResponseEntity<?> createItinerary(@PathVariable Integer travelId, @RequestBody ItineraryCreateRequestDto itineraryRegisterDto){
        try{
            Integer tripId;
            tripId = travelId;
            Trip trip = tripRepository.getById(tripId);

            Itinerary itinerary = new Itinerary(trip,
                    itineraryRegisterDto.getDate(),
                    itineraryRegisterDto.getTime(),
                    itineraryRegisterDto.getTitle(),
                    itineraryRegisterDto.getLocation(),
                    itineraryRegisterDto.getDescription());

            itinerary = itineraryRepository.save(itinerary);
            ItineraryCreateResponseDto responseDto = new ItineraryCreateResponseDto("itinerary_" + itinerary.getItineraryId().toString(), "Itinerary added successfully");
            return ResponseEntity.status(201).body(responseDto);
        }catch (Exception e){
            ItineraryCreateResponseDto responseDto = new ItineraryCreateResponseDto(null, e.getMessage());
            return ResponseEntity.badRequest().body(responseDto);
        }

    }

    @GetMapping(path = "{travelId}/itineraries")
    public ResponseEntity<?> getAllItinerariesForTrip(@PathVariable Integer travelId){
        try{
            Integer tripId;
            tripId = travelId;

            List<Itinerary> itineraries = itineraryService.getItinerariesByTripId(tripId);
            if (itineraries.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            List<ItineraryResponseDto> responseDtos = new ArrayList<>();
            for(Itinerary itinerary:itineraries){
                ItineraryResponseDto itineraryResponseDto = new ItineraryResponseDto(
                        itinerary.getItineraryId(),
                        itinerary.getTitle(),
                        itinerary.getDescription(),
                        itinerary.getDate(),
                        itinerary.getTime(),
                        itinerary.getLocation()

                );
                responseDtos.add(itineraryResponseDto);
            }
            return ResponseEntity.ok(responseDtos);
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(null);
        }


    }

    @PutMapping(path = "{travelId}/itineraries/{itineraryId}")
    public ResponseEntity<?> updateItinerary(@PathVariable Integer travelId, @PathVariable Integer itineraryId, @RequestBody ItineraryUpdateRequestDto itineraryUpdateRequestDto){
        Integer tripId;
        tripId = travelId;

        Itinerary itinerary = itineraryService.getItineraryById(itineraryId);
        if(itinerary!=null){
            itinerary.setTitle(itineraryUpdateRequestDto.getTitle());
            itinerary.setDescription(itineraryUpdateRequestDto.getDescription());
            itinerary.setDate(itineraryUpdateRequestDto.getDate());
            itinerary.setTime(itineraryUpdateRequestDto.getTime());
            itinerary.setLocation(itineraryUpdateRequestDto.getLocation());

            itineraryRepository.save(itinerary);
            return ResponseEntity.ok(new ItineraryUpdateResponseDto("Itinerary updated successfully"));
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path = "{travelId}/itineraries/{itineraryId}")
    public ResponseEntity<?> deleteItinerary(@PathVariable Integer travelId, @PathVariable Integer itineraryId){
        Itinerary itinerary = itineraryService.getItineraryById(itineraryId);

        if(itinerary !=null){
            itineraryRepository.deleteById(itineraryId);
            return ResponseEntity.ok(new ItineraryUpdateResponseDto("Itinerary removed successfully"));
        }else{
            return ResponseEntity.notFound().build();
        }
    }

}
