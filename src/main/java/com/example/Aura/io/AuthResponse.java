package com.example.Aura.io;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class AuthResponse {

    private String email;
    private String roll;
    private String token;

}
