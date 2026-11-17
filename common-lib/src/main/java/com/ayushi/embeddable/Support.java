package com.ayushi.embeddable;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.*;

@Embeddable
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Support {
    private String email;
    private String phone;
    private String hours;
}
