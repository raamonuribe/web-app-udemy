package com.ramon.mobileappws.ui.controller;

import com.ramon.mobileappws.exceptions.UserServiceException;
import com.ramon.mobileappws.service.UserService;
import com.ramon.mobileappws.shared.dto.UserDto;
import com.ramon.mobileappws.ui.model.request.UserDetailsRequestModel;
import com.ramon.mobileappws.ui.model.response.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserRest> getUsers(@RequestParam(value = "page", defaultValue = "1") int page,
                                   @RequestParam(value = "limit", defaultValue = "25") int limit) {
        List<UserRest> returnValue;

        List<UserDto> users = userService.getUsers(page, limit);

        // Using streams to map list of userDto to list of userRest objs
        returnValue = users.stream().map(temp -> {
            UserRest user = new UserRest();
            BeanUtils.copyProperties(temp, user);
            return user;
        }).collect(Collectors.toList());

        return returnValue;
    }

    @GetMapping("/{userId}")
    public UserRest getUser(@PathVariable String userId) {
        UserRest returnValue = new UserRest();

        UserDto userDto = userService.getUser(userId);
//        BeanUtils.copyProperties(userDto, return
        ModelMapper modelMapper = new ModelMapper();
        returnValue = modelMapper.map(userDto, UserRest.class);
        return returnValue;
    }

    @PostMapping("/register")
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) {
        UserRest returnValue = new UserRest();

        if (userDetails.getEmail().isEmpty()) throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

//        UserDto userDto = new UserDto();
//        BeanUtils.copyProperties(userDetails, userDto);
        // Same thing but modelMapper has more features that are needed for this project
        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto = modelMapper.map(userDetails, UserDto.class);

        UserDto createdUser = userService.createUser(userDto);
        returnValue = modelMapper.map(createdUser, UserRest.class);

        return returnValue;
    }

    @PutMapping("/{userId}")
    public UserRest updateUser(@PathVariable String userId, @RequestBody UserDetailsRequestModel userDetails) {

        UserRest returnValue = new UserRest();

        if (userDetails.getFirstName().isEmpty()) throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails, userDto);

        UserDto updatedUser = userService.updateUser(userId, userDto);
//        BeanUtils.copyProperties(updatedUser, returnValue);
        ModelMapper modelMapper = new ModelMapper();
        returnValue = modelMapper.map(updatedUser, UserRest.class);

        return returnValue;
    }
    @DeleteMapping("/{userId}")
    public OperationStatusModel deleteUser(@PathVariable String userId) {
        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.DELETE.name());

        userService.deleteUser(userId);
        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());

        return returnValue;
    }

}
