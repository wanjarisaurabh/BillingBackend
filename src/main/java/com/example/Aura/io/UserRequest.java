package com.example.Aura.io;

import lombok.*;

import java.security.PrivateKey;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {

    private String name ;
    private String email;
    private String password;
    private String role;



}
