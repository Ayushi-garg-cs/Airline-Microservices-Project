package com.ayushi.services.impl;

import com.ayushi.mapper.AirportMapper;
import com.ayushi.model.Airport;
import com.ayushi.model.City;
import com.ayushi.payload.request.AirportRequest;
import com.ayushi.payload.response.AirportResponse;
import com.ayushi.repository.AirportRepository;
import com.ayushi.repository.CityRepository;
import com.ayushi.services.AirportService;
import jakarta.persistence.Cacheable;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AirportServiceImpl implements AirportService {

    private final AirportRepository airportRepository;
    private final CityRepository cityRepository;

    @Override
    @Transactional
    public AirportResponse createAirport(AirportRequest request) throws Exception {
        if (airportRepository.findByIataCode(request.getIataCode()).isPresent()) {
            throw new Exception("Airport with IATA code " + request.getIataCode() + " already exists.");
        }

        City city = cityRepository.findById(request.getCityId())
                .orElseThrow(() -> new Exception("City not found with id: " + request.getCityId()));

        Airport airport = AirportMapper.toEntity(request);
        airport.setCity(city);

        Airport savedAirport = airportRepository.save(airport);
        return AirportMapper.toResponse(savedAirport);
    }

    @Override
    @Transactional
    public List<AirportResponse> createBulkAirports(List<AirportRequest> requests)
            throws Exception {
        List<AirportResponse> createdAirports = new ArrayList<>();
        List<String> skippedCodes = new ArrayList<>();

        for (AirportRequest request : requests) {
            if (airportRepository.findByIataCode(request.getIataCode()).isPresent()) {
                skippedCodes.add(request.getIataCode() + " (already exists)");
                continue;
            }

            Optional<City> cityOpt = cityRepository.findById(request.getCityId());
            if (cityOpt.isEmpty()) {
                skippedCodes.add(request.getIataCode() + " (city not found with id: " + request.getCityId() + ")");
                continue;
            }

            Airport airport = AirportMapper.toEntity(request);
            airport.setCity(cityOpt.get());

            Airport savedAirport = airportRepository.save(airport);
            createdAirports.add(AirportMapper.toResponse(savedAirport));
        }

        if (!skippedCodes.isEmpty()) {
            log.info("Bulk airport creation - skipped: {}", skippedCodes);
        }
        log.info("Bulk airport creation - created {} out of {} airports", createdAirports.size(), requests.size());

        return createdAirports;
    }

    @Override
//    @Transactional(readOnly = true)
//    @Cacheable(cacheNames = "airports", key = "#id")
    public AirportResponse getAirportById(Long id) {
        Airport airport = airportRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Airport not found with id: " + id));
        return AirportMapper.toResponse(airport);
    }


    @Override
//    @Transactional(readOnly = true)
//    @Cacheable(cacheNames = "allAirports")
    public List<AirportResponse> getAllAirports() {
        return airportRepository.findAll().stream()
                .map(AirportMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
//    @Caching(evict = {
//            @CacheEvict(cacheNames = "airports", key = "#id"),
//            @CacheEvict(cacheNames = "allAirports", allEntries = true),
//            @CacheEvict(cacheNames = "airportsByIata", allEntries = true),
//            @CacheEvict(cacheNames = "airportsByCity", allEntries = true)
//    })
    public AirportResponse updateAirport(Long id, AirportRequest request) throws Exception {
        Airport existingAirport = airportRepository.findById(id)
                .orElseThrow(() -> new Exception("Airport not found with id: " + id));

        if (request.getIataCode() != null
                && !existingAirport.getIataCode().equals(request.getIataCode())
                && airportRepository.findByIataCode(request.getIataCode()).isPresent()) {
            throw new Exception("IATA code " + request.getIataCode() + " is already taken.");
        }

        if (request.getCityId() != null) {
            City newCity = cityRepository.findById(request.getCityId())
                    .orElseThrow(() -> new Exception("City not found with id: " + request.getCityId()));
            existingAirport.setCity(newCity);
        }

        AirportMapper.updateEntity(request, existingAirport);

        Airport updatedAirport = airportRepository.save(existingAirport);
        return AirportMapper.toResponse(updatedAirport);
    }

    @Override
    @Transactional
//    @Caching(evict = {
//            @CacheEvict(cacheNames = "airports", key = "#id"),
//            @CacheEvict(cacheNames = "allAirports", allEntries = true),
//            @CacheEvict(cacheNames = "airportsByIata", allEntries = true),
//            @CacheEvict(cacheNames = "airportsByCity", allEntries = true)
//    })
    public void deleteAirport(Long id) throws Exception {
        Airport airport = airportRepository.findById(id)
                .orElseThrow(() -> new Exception("Airport not found with id: " + id));
        airportRepository.delete(airport);
    }

    @Override
//    @Transactional(readOnly = true)
//    @Cacheable(cacheNames = "airportsByCity", key = "#cityId")
    public List<AirportResponse> getAirportsByCityId(Long cityId) {
        return airportRepository.findByCityId(cityId).stream()
                .map(AirportMapper::toResponse)
                .collect(Collectors.toList());
    }
}

