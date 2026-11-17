package com.ayushi.payload.response;

import jdk.jshell.Snippet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AirlineDropdownItem {
    private Long id;
    private String name;;
    private String iataCode;
    private String icaoCode;
    private String logoUrl;
    private String country;
}
