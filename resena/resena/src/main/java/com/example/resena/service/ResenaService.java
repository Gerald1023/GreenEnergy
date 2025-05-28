package com.example.resena.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.resena.Repository.ResenaRepository;
import com.example.resena.model.Resena;
import com.example.resena.webclient.ClienteClient;
import com.example.resena.webclient.UserClient;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ResenaService {

    @Autowired
    private ResenaRepository resenaRepository;

    @Autowired
    private UserClient userClient;

    @Autowired
    private ClienteClient clienteClient;

    public List<Resena> getResenas() {
        return resenaRepository.findAll();
    }

    public Resena saveResena(Resena nuevaResena) {
        Map<String, Object> user = userClient.getUsuarioById(nuevaResena.getIdUsuario());
        if (user == null || user.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado");
        }

        Map<String, Object> servicio = clienteClient.getServicioById(nuevaResena.getIdServicio());
        if (servicio == null || servicio.isEmpty()) {
            throw new RuntimeException("Servicio no encontrado");
        }

        if (nuevaResena.getComentario() == null || nuevaResena.getComentario().isBlank()) {
            throw new IllegalArgumentException("El comentario no puede estar vacio");
        }

        return resenaRepository.save(nuevaResena);
    }

    public Resena getResenaPorId(Long id) {
        return resenaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resena no encontrada"));
    }
}