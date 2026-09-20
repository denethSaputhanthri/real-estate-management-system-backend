package com.realestate.system.service.impl;


import com.realestate.system.exception.ResourceNotFoundException;
import com.realestate.system.entity.User;
import com.realestate.system.repository.UserRepository;
import com.realestate.system.service.UserService;
import lombok.RequiredArgsConstructor;
import com.realestate.system.model.dto.request.CreateUserRequest;
import com.realestate.system.model.dto.request.UpdateUserRequest;
import com.realestate.system.model.dto.response.UserResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse createUser(CreateUserRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhone(request.getPhone());
       User savedUser = userRepository.save(user);
        return mapToResponse(savedUser);
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user= userRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User not found with id: " + id));
        return mapToResponse(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public UserResponse updateUser(Long id, UpdateUserRequest request) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User not found this ID : " + id));

        if (request.getName() != null) {
            existingUser.setName(request.getName());
        }

        if (request.getEmail() != null) {
            existingUser.setEmail(request.getEmail());
        }

        if (request.getPhone() != null) {
            existingUser.setPhone(request.getPhone());
        }

        if (request.getPassword() != null) {
            existingUser.setPassword(request.getPassword());
        }
        User savedUser = userRepository.save(existingUser);
        return mapToResponse(savedUser) ;
    }

    @Override
    public void deleteUser(Long id) {
        User exitingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found : " + id));
        userRepository.delete(exitingUser);
    }

    @Override
    public void registerUser(String email, String password, String role) {
        //wait for the implementation
    }

    @Override
    public boolean authenticateUser(String email, String password) {
        return false;
    }

    @Override
    public void updateUserStatus(String email, String status) {
        //wait for the implementation
    }
    private UserResponse mapToResponse(User user) {

        UserResponse response = new UserResponse();

        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setRole(user.getRole());
        response.setStatus(user.getStatus());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());

        return response;
    }
}
