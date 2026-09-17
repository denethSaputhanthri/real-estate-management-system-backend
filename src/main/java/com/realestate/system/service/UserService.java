package com.realestate.system.service;


import com.realestate.system.model.dto.request.CreateUserRequest;
import com.realestate.system.model.dto.request.UpdateUserRequest;
import com.realestate.system.model.dto.response.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse createUser(CreateUserRequest request);

    UserResponse getUserById(Long id);

    List<UserResponse> getAllUsers();

    UserResponse updateUser(Long id, UpdateUserRequest request);

    void deleteUser(Long id);

    void registerUser(String email, String password, String role);

    boolean authenticateUser(String email, String password);

    void updateUserStatus(String email, String status);

}
