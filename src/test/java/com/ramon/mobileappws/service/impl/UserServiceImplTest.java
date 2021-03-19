package com.ramon.mobileappws.service.impl;

import com.ramon.mobileappws.io.entity.UserEntity;
import com.ramon.mobileappws.io.repository.UserRepository;
import com.ramon.mobileappws.shared.dto.UserDto;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() throws Exception {
        // initMocks is deprecated
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setFirstName("Lelouch");
        userEntity.setLastName("Lamperouge");
        userEntity.setUserId("fjdlsajfel2dsa43f42de");
        userEntity.setEncryptedPassword("jfdlsau3u84a5u32890rew89e4rt423");
        userEntity.setEmail("test@test.com");

        when(userRepository.findByUserId("fjdlsajfel2dsa43f42de")).thenReturn(userEntity);

        UserDto userDto = userService.getUser(userEntity.getUserId());

        // Assertions
        assertNotNull(userDto);
        assertEquals("Lelouch", userDto.getFirstName());
        assertNotEquals("vi Britannia", userDto.getLastName());
    }

    @Test
    void testGetUser_throwUsernameNotFoundException() {
        when(userRepository.findByUserId(anyString())).thenReturn(null);


        // Test
        assertThrows(UsernameNotFoundException.class,
                    () -> {
                        userService.getUser("test@test.com");
                    }
                );
    }
}