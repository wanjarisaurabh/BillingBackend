package com.example.Aura.service.impl;

import com.example.Aura.entity.UserEntity;
import com.example.Aura.io.UserRequest;
import com.example.Aura.io.UserResponse;
import com.example.Aura.repository.UserRepository;
import com.example.Aura.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

   private final UserRepository userRepository;

private final PasswordEncoder passwordEncoder;



    @Override
    public UserResponse createUser(UserRequest request) {
        UserEntity newEntity =convertToEntity(request);

        System.out.println(request.getEmail());
    
    newEntity = userRepository.save(newEntity);
    return converToResponse(newEntity);

    }

    private UserEntity convertToEntity(UserRequest request) {

      return UserEntity.builder()
                .userId(UUID.randomUUID().toString())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole().toUpperCase())
                .name(request.getName())
                .build();



    }


    private UserResponse converToResponse(UserEntity newEntity) {
       return UserResponse.builder()
                .name(newEntity.getName())
                .email(newEntity.getEmail())
                .userId(newEntity.getUserId())
                .createdAt(newEntity.getCreatedAt())
                .updatedAt(newEntity.getUpdatedAt())
                .role(newEntity.getRole())
                .build();
    }


    @Override
    public String getUserRole(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("User not found for the email :-  "+email));

        return user.getRole();
    }

    @Override
    public List<UserResponse> readUser() {
        return userRepository.findAll()
                .stream()
                .map((user)-> converToResponse(user))
                .collect(Collectors.toList());

    }

    @Override
    public void deleteUser(String id) {

       UserEntity existinguser=  userRepository.findByUserId(id)
                .orElseThrow(()-> new UsernameNotFoundException("user not found for the id:-  "+id));

        userRepository.delete((existinguser));

    }
}
