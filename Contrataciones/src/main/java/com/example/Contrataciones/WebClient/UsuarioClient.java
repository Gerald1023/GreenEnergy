package com.example.Contrataciones.WebClient;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Component
public class UsuarioClient {

    private final WebClient webClient;

    public UsuarioClient(@Value("${Usuario-Service.url}") String UsuarioServiceUrl) {
        this.webClient = WebClient.builder().baseUrl(UsuarioServiceUrl).build();
    }

    public Map<String, Object> getProyectoById(Long id) {
        return this.webClient.get()
                .uri("/{id}", id)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(),
                        response -> response.bodyToMono(String.class)
                                .flatMap(body -> Mono.error(new RuntimeException("Proyecto no encontrado: " + body))))
                .onStatus(status -> status.is5xxServerError(),
                        response -> response.bodyToMono(String.class)
                                .flatMap(body -> Mono
                                        .error(new RuntimeException("Error del servidor Proyecto: " + body))))
                .bodyToMono(Map.class)
                .doOnNext(body -> System.out.println("Respuesta Proyecto: " + body))
                .block();
    }
}