package com.example.Aura.service;


import com.example.Aura.io.UserRequest;
import com.example.Aura.io.UserResponse;
import org.springframework.stereotype.Service;
import java.util.*;

public interface UserService {
    UserResponse createUser(UserRequest request);

    String getUserRole(String email);

    List<UserResponse> readUser();

    void deleteUser(String id);
}
