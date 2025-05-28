package com.example.resena.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.resena.model.Resena;
import com.example.resena.service.ResenaService;
import com.example.resena.webclient.ClienteClient;
import com.example.resena.webclient.UserClient;

@RestController
@RequestMapping("/api/resena")
public class ResenaController {
    @Autowired
    ResenaService resenaService;
    @Autowired
    UserClient userClient;
    @Autowired
    ClienteClient clienteClient;

    @GetMapping
    public ResponseEntity<List<Resena>> getResenas() {
        List<Resena> resenas = resenaService.getResenas();
        return ResponseEntity.ok(resenas);
    }

    @PostMapping
    public ResponseEntity<?> crearResena(@RequestBody Resena nuevResena) {
        LocalDate despues = LocalDate.of(2025, 5, 28);

        if (nuevResena.getComentario() == null || nuevResena.getComentario().length() < 1 || nuevResena.getComentario().length() > 100) {
            return ResponseEntity.badRequest()
                    .body("El comentario debe tener entre 1 y 100 caracteres.");
        }
        if (nuevResena.getFechaComentario() == null || nuevResena.getFechaComentario().isBefore(despues)) {
            return ResponseEntity.badRequest()
                    .body("La fecha debe ser igual o posterior al 28 de mayo de 2025.");
        }

        try {
            Resena resena = resenaService.saveResena(nuevResena);
            return ResponseEntity.status(201).body(resena);
        } catch (RuntimeException e) {
            // Captura error por usuario o servicio no encontrado
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resena> obtenerResenaPorId(@PathVariable Long id) {
        try {
            Resena resena = resenaService.getResenaPorId(id);
            return ResponseEntity.ok(resena);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}

