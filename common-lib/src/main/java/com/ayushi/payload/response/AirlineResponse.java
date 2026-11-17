package com.ayushi.payload.response;

import com.ayushi.embeddable.Support;
import com.ayushi.enums.AirlineStatus;
import com.ayushi.payload.dto.UserDTO;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AirlineResponse {
    private Long id;

    private String iataCode;
    private String icaoCode;

    private String country;

    private String name;
    private String alias;

    private String logoUrl;
    private String website;

    private String alliance;
    private AirlineStatus status;

    private Instant createdAt;
    private Instant updatedAt;

    private Long ownerId;
    private UserDTO owner;
    private Long updatedById;

    private Long headquartersCityId;
    private Support support;
}
