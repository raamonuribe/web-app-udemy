package com.ramon.mobileappws.ui.controller;

import com.ramon.mobileappws.exceptions.UserServiceException;
import com.ramon.mobileappws.service.UserService;
import com.ramon.mobileappws.shared.dto.UserDto;
import com.ramon.mobileappws.ui.model.request.UserDetailsRequestModel;
import com.ramon.mobileappws.ui.model.response.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public UserRest getUser(@PathVariable String userId) {
        UserRest returnValue = new UserRest();

        UserDto userDto = userService.getUser(userId);
        BeanUtils.copyProperties(userDto, returnValue);
        return returnValue;
    }

    @PostMapping("/register")
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception {
        UserRest returnValue = new UserRest();

        if (userDetails.getEmail().isEmpty()) throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails, userDto);

        UserDto createdUser = userService.createUser(userDto);
        BeanUtils.copyProperties(createdUser, returnValue);
        return returnValue;
    }

    @PutMapping("/{userId}")
    public UserRest updateUser(@PathVariable String userId, @RequestBody UserDetailsRequestModel userDetails) {

        UserRest returnValue = new UserRest();

        if (userDetails.getFirstName().isEmpty()) throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails, userDto);

        UserDto updatedUser = userService.updateUser(userId, userDto);
        BeanUtils.copyProperties(updatedUser, returnValue);
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
