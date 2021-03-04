package com.ramon.mobileappws.service.impl;

import com.ramon.mobileappws.exceptions.AddressServiceException;
import com.ramon.mobileappws.io.entity.AddressEntity;
import com.ramon.mobileappws.io.entity.UserEntity;
import com.ramon.mobileappws.io.repository.AddressRepository;
import com.ramon.mobileappws.io.repository.UserRepository;
import com.ramon.mobileappws.service.AddressService;
import com.ramon.mobileappws.shared.dto.AddressDto;
import com.ramon.mobileappws.ui.model.response.ErrorMessages;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl  implements AddressService {

    private UserRepository userRepository;
    private AddressRepository addressRepository;

    @Autowired
    public AddressServiceImpl(UserRepository userRepository, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    public List<AddressDto> getAddresses(String userId) {
        List<AddressDto> returnValue = new ArrayList<>();

        // get specified user
        UserEntity userEntity = userRepository.findByUserId(userId);

        // Check if user exists
        if (userEntity == null) return returnValue;

        // Get list of addresses
        List<AddressEntity> addresses = addressRepository.findAllByUserDetails(userEntity);

        // Map List of Address Entities to List of AddressDtos
        ModelMapper modelMapper = new ModelMapper();
        returnValue = addresses.stream().map(temp -> {
            AddressDto address = modelMapper.map(temp, AddressDto.class);
            return address;
        }).collect(Collectors.toList());


        return returnValue;
    }

    @Override
    public AddressDto getAddress(String addressId) {
        AddressDto returnValue = new AddressDto();

        AddressEntity addressEntity = addressRepository.findByAddressId(addressId);
        // Check if address exists
        if (addressEntity == null) throw new AddressServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        // Map addressEntity to addressDto
        ModelMapper modelMapper = new ModelMapper(); // Remember to inject model mapper in next project to avoid instantiating so many times
        returnValue = modelMapper.map(addressEntity, AddressDto.class);

        return returnValue;

    }
}
