package com.ayushi.payload.request;

import com.ayushi.embeddable.Address;
import com.ayushi.embeddable.GeoCode;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.ZoneId;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AirportRequest {

    @NotBlank(message = "IATA code is mandatory")
    @Size(min=3,max=3,message = "IATA code must be exactly 3 characters")
    private String iataCode;
    @NotBlank(message = "Airport name is mandatory")
    private String name;
    private ZoneId timeZone;
    @Valid
    private Address address;
    @NotNull(message = "City ID is mandatory")
    private Long cityId;
    @Valid
    private GeoCode geoCode;

}
