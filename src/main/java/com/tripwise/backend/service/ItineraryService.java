package com.tripwise.backend.service;

import com.tripwise.backend.entity.Itinerary;
import com.tripwise.backend.repository.ItineraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItineraryService {

    @Autowired
    private ItineraryRepository itineraryRepository;

    public List<Itinerary> getItinerariesByTripId(Integer tripId) {
        return itineraryRepository.findByTripTripId(tripId);
    }

    public Itinerary getItineraryById(Integer itineraryId){
        return itineraryRepository.findById(itineraryId).orElse(null);
    }


}
