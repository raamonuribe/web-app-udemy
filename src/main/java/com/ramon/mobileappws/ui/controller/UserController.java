package com.ramon.mobileappws.ui.controller;

import com.ramon.mobileappws.service.UserService;
import com.ramon.mobileappws.shared.dto.UserDto;
import com.ramon.mobileappws.ui.model.request.UserDetailsRequestModel;
import com.ramon.mobileappws.ui.model.response.UserRest;
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

    @GetMapping
    public String getUser() {
        return "Get User was called.";
    }

    @PostMapping("/register")
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) {
        UserRest returnValue = new UserRest();

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails, userDto);

        UserDto createdUser = userService.createUser(userDto);
        BeanUtils.copyProperties(createdUser, returnValue);
        return returnValue;
    }

    @PutMapping
    public String updateUser() {
        return "Update User was called.";
    }
    @DeleteMapping
    public String deleteUser() {
        return "Delete User was called";
    }
}
