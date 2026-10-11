package com.fahmi.personalonlinestore.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressResponse {
    private String id;
    private String recipientName;
    private String phoneNumber;
    private String streetAddress;
    private String city;
    private String postalCode;
    private String label;
    private boolean isPrimary;
    private LocalDateTime createdAt;
}
