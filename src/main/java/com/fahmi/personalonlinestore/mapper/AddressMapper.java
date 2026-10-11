package com.fahmi.personalonlinestore.mapper;

import com.fahmi.personalonlinestore.dto.response.AddressResponse;
import com.fahmi.personalonlinestore.entity.Address;

public class AddressMapper {
    public static AddressResponse toResponse(Address address) {
        if (address == null) return null;
        return AddressResponse.builder()
                .id(address.getId())
                .recipientName(address.getRecipientName())
                .phoneNumber(address.getPhoneNumber())
                .streetAddress(address.getStreetAddress())
                .city(address.getCity())
                .postalCode(address.getPostalCode())
                .label(address.getLabel())
                .isPrimary(address.isPrimary())
                .createdAt(address.getCreatedAt())
                .build();
    }
}
