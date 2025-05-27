package com.example.Ubicacion.WebClient;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UsuarioClient {

    private final WebClient.Builder webClientBuilder;

    public boolean validarUsuarioExistente(Long usuarioId) {
        return webClientBuilder.build()
            .get()
            .uri("http://localhost:8081/api/v1/usuarios/{id}", usuarioId)
            .retrieve()
            .toBodilessEntity()
            .block()
            .getStatusCode()
            .is2xxSuccessful();
    }aaaa
}