package com.reservation.web.service;

import com.reservation.web.dto.RegistrationDto;
import com.reservation.web.models.UserEntity;

public interface UserService {
    void saveUser(RegistrationDto registrationDto);

    UserEntity findByEmail(String email);

    UserEntity findByUsername(String username);
}
