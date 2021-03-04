package com.ramon.mobileappws.service.impl;

import com.ramon.mobileappws.service.AddressService;
import com.ramon.mobileappws.shared.dto.AddressDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl  implements AddressService {

    @Override
    public List<AddressDto> getAddresses(String userId) {
        return null;
    }
}
