package com.ramon.mobileappws.service;

import com.ramon.mobileappws.shared.dto.AddressDto;

import java.util.List;

public interface AddressService {
    List<AddressDto> getAddresses(String userId);
}
