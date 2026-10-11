package com.fahmi.personalonlinestore.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressRequest {
    @NotBlank(message = "Recipient name cannot be blank")
    private String recipientName;

    @NotBlank(message = "Phone number cannot be blank")
    private String phoneNumber;

    @NotBlank(message = "Street address cannot be blank")
    private String streetAddress;

    @NotBlank(message = "City cannot be blank")
    private String city;

    private String postalCode;

    private String label;

    private Boolean isPrimary;
}
