package com.fahmi.personalonlinestore.service;

import com.fahmi.personalonlinestore.dto.request.AddressRequest;
import com.fahmi.personalonlinestore.dto.response.AddressResponse;

import java.util.List;

public interface AddressService {
    AddressResponse createAddress(AddressRequest request);

    List<AddressResponse> getMyAddresses();

    AddressResponse updateAddress(String id, AddressRequest request);

    AddressResponse setPrimaryAddress(String id);

    void deleteAddress(String id);
}
