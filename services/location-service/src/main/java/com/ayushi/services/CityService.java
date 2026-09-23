

import com.ayushi.payload.request.CityRequest;
import com.ayushi.payload.response.CityResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CityService {

    // ---------- Core CRUD ----------
    CityResponse createCity(CityRequest request) throws OperationNotPermittedException;
    List<CityResponse> createBulkCities(List<CityRequest> requests) throws OperationNotPermittedException;
    CityResponse getCityById(Long id) throws ResourceNotFoundException;

    CityResponse updateCity(Long id, CityRequest request) throws ResourceNotFoundException, OperationNotPermittedException;
    void deleteCity(Long id) throws ResourceNotFoundException;
    Page<CityResponse> getAllCities(Pageable pageable);

    // ---------- Search & Query ----------
    Page<CityResponse> searchCities(String keyword, Pageable pageable);
    Page<CityResponse> getCitiesByCountryCode(String countryCode, Pageable pageable);

    // ---------- Validation ----------
    boolean cityExists(String cityCode);
    boolean validateCityCode(String cityCode);
}
