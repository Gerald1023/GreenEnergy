package com.example.Usuario.model;

imnport lombok.Data;

@Data

public class LoginRequest {
    private String email;
    private String contrasena;
}
