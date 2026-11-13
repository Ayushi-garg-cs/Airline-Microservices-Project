package com.ayushi.services;

import com.ayushi.payload.request.AirportRequest;
import com.ayushi.payload.response.AirportResponse;

import java.util.List;

public interface AirportService {

    AirportResponse createAirport(AirportRequest request) throws Exception;
    List<AirportResponse> createBulkAirports(List<AirportRequest> requests) throws Exception;
    AirportResponse getAirportById(Long id);

    List<AirportResponse> getAllAirports();
    AirportResponse updateAirport(Long id, AirportRequest request) throws Exception;
    void deleteAirport(Long id) throws  Exception;
    List<AirportResponse> getAirportsByCityId(Long cityId);
}
