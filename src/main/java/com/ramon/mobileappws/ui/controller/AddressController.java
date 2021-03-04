package com.ramon.mobileappws.ui.controller;

import com.ramon.mobileappws.service.AddressService;
import com.ramon.mobileappws.shared.dto.AddressDto;
import com.ramon.mobileappws.ui.model.response.AddressRest;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping
public class AddressController {

    private AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping(path = "/users/{userId}/addresses")
    public List<AddressRest> getUserAddresses(@PathVariable String userId) {
        List<AddressRest> returnValue = new ArrayList<>();

        List<AddressDto> addresses = addressService.getAddresses(userId);

        ModelMapper modelMapper = new ModelMapper();
//        returnValue = addresses.stream().map(temp -> {
//            AddressRest address = modelMapper.map(temp, AddressRest.class);
//            return address;
//        }).collect(Collectors.toList());

        // Remember to check if list is empty //

        // How to map lists using model mapper
        Type listType = new TypeToken<List<AddressRest>>() {}.getType();
        returnValue = modelMapper.map(addresses, listType);

        return returnValue;

    }

    @GetMapping(path = "/users/{userId}/addresses/{addressId}")
    public AddressRest getUserAddress(@PathVariable String addressId) {
        AddressRest returnValue = new AddressRest();

        AddressDto address = addressService.getAddress(addressId);

        ModelMapper modelMapper = new ModelMapper();
        returnValue = modelMapper.map(address, AddressRest.class);

        return returnValue;
    }
}
