package com.ramon.mobileappws.service;

import com.ramon.mobileappws.shared.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto userDto);
    UserDto getUserByEmail(String email);
    UserDto getUser(String userId);
    UserDto updateUser(String userId, UserDto userDto);
    void deleteUser(String userId);
}
