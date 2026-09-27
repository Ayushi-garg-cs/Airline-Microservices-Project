package com.ayushi.services;

import com.ayushi.payload.request.AirportRequest;
import com.ayushi.payload.response.AirportResponse;

import java.util.List;

public interface AirportService {

    AirportResponse createAirport(AirportRequest request) throws AirportException, CityException;
    List<AirportResponse> createBulkAirports(List<AirportRequest> requests) throws AirportException, CityException;
    AirportResponse getAirportById(Long id);

    List<AirportResponse> getAllAirports();
    AirportResponse updateAirport(Long id, AirportRequest request) throws AirportException, CityException;
    void deleteAirport(Long id) throws AirportException;
    List<AirportResponse> getAirportsByCityId(Long cityId);
}
