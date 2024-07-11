package com.reservation.web.service.impl;

import com.reservation.web.dto.RegistrationDto;
import com.reservation.web.models.Role;
import com.reservation.web.models.UserEntity;
import com.reservation.web.repository.RoleRepository;
import com.reservation.web.repository.UserRepository;
import com.reservation.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(RegistrationDto registrationDto){
        UserEntity user = new UserEntity();
        user.setEmail(registrationDto.getEmail());
        user.setUsername(registrationDto.getUsername());
        user.setName(registrationDto.getName());
        user.setSurname(registrationDto.getSurname());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        Role role = roleRepository.findByName("USER");
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }

    @Override
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
