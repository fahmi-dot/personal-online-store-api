package com.fahmi.personalonlinestore.service.impl;

import com.fahmi.personalonlinestore.dto.request.AddressRequest;
import com.fahmi.personalonlinestore.dto.response.AddressResponse;
import com.fahmi.personalonlinestore.entity.Address;
import com.fahmi.personalonlinestore.entity.User;
import com.fahmi.personalonlinestore.exception.CustomException;
import com.fahmi.personalonlinestore.mapper.AddressMapper;
import com.fahmi.personalonlinestore.repository.AddressRepository;
import com.fahmi.personalonlinestore.service.AddressService;
import com.fahmi.personalonlinestore.service.UserService;
import com.fahmi.personalonlinestore.util.TokenHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final UserService userService;
    private final TokenHolder tokenHolder;

    private User getCurrentUser() {
        String username = tokenHolder.getUsername();
        if ("admin".equals(username)) {
            throw new CustomException.AuthorizationException("Admin cannot manage customer shipping addresses.");
        }
        return userService.findUserByUsername(username);
    }

    @Override
    @Transactional
    public AddressResponse createAddress(AddressRequest request) {
        User user = getCurrentUser();
        List<Address> existing = addressRepository.findByUserIdOrderByIsPrimaryDescCreatedAtDesc(user.getId());

        boolean isPrimary = existing.isEmpty() || Boolean.TRUE.equals(request.getIsPrimary());
        if (isPrimary && !existing.isEmpty()) {
            addressRepository.resetPrimaryByUserId(user.getId());
        }

        Address address = Address.builder()
                .recipientName(request.getRecipientName())
                .phoneNumber(request.getPhoneNumber())
                .streetAddress(request.getStreetAddress())
                .city(request.getCity())
                .postalCode(request.getPostalCode())
                .label(request.getLabel() != null && !request.getLabel().isBlank() ? request.getLabel() : "Alamat")
                .isPrimary(isPrimary)
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();

        addressRepository.save(address);
        return AddressMapper.toResponse(address);
    }

    @Override
    public List<AddressResponse> getMyAddresses() {
        User user = getCurrentUser();
        List<Address> addresses = addressRepository.findByUserIdOrderByIsPrimaryDescCreatedAtDesc(user.getId());
        return addresses.stream().map(AddressMapper::toResponse).toList();
    }

    @Override
    @Transactional
    public AddressResponse updateAddress(String id, AddressRequest request) {
        User user = getCurrentUser();
        Address address = addressRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new CustomException.ResourceNotFoundException("Address not found."));

        if (Boolean.TRUE.equals(request.getIsPrimary()) && !address.isPrimary()) {
            addressRepository.resetPrimaryByUserId(user.getId());
            address.setPrimary(true);
        }

        address.setRecipientName(request.getRecipientName());
        address.setPhoneNumber(request.getPhoneNumber());
        address.setStreetAddress(request.getStreetAddress());
        address.setCity(request.getCity());
        address.setPostalCode(request.getPostalCode());
        if (request.getLabel() != null && !request.getLabel().isBlank()) {
            address.setLabel(request.getLabel());
        }

        addressRepository.save(address);
        return AddressMapper.toResponse(address);
    }

    @Override
    @Transactional
    public AddressResponse setPrimaryAddress(String id) {
        User user = getCurrentUser();
        Address address = addressRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new CustomException.ResourceNotFoundException("Address not found."));

        addressRepository.resetPrimaryByUserId(user.getId());
        address.setPrimary(true);
        addressRepository.save(address);
        return AddressMapper.toResponse(address);
    }

    @Override
    @Transactional
    public void deleteAddress(String id) {
        User user = getCurrentUser();
        Address address = addressRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new CustomException.ResourceNotFoundException("Address not found."));

        boolean wasPrimary = address.isPrimary();
        addressRepository.delete(address);

        if (wasPrimary) {
            List<Address> remaining = addressRepository.findByUserIdOrderByIsPrimaryDescCreatedAtDesc(user.getId());
            if (!remaining.isEmpty()) {
                Address first = remaining.get(0);
                first.setPrimary(true);
                addressRepository.save(first);
            }
        }
    }
}
