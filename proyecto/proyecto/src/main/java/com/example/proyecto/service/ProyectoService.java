package com.example.proyecto.service;


import com.example.proyecto.model.Proyecto;
import com.example.proyecto.repository.ProyectoRepository;
import com.example.proyecto.webclient.ClienteClient;
import com.example.proyecto.webclient.ContratacionClient;
import com.example.proyecto.webclient.UsuarioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProyectoService {

    @Autowired
    private ProyectoRepository repository;

    @Autowired
    private ClienteClient clienteClient;

    @Autowired
    private UsuarioClient usuarioClient;

    @Autowired
    private ContratacionClient contratacionClient;

    public Proyecto crearProyecto(Proyecto proyecto) {
        // Validar existencia del estado (cliente)
        Map<String, Object> estado = clienteClient.getEstadoById(proyecto.getEstadoId());
        if (estado == null || estado.isEmpty()) {
            throw new RuntimeException("Estado no encontrado");
        }

        //Validar existencia del usuario
        Map<String, Object> usuario = usuarioClient.getUsuarioById(proyecto.getUsuarioId());
        if (usuario == null || usuario.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado");
        }

        // Validar existencia de la contratación
        Map<String, Object> contratacion = contratacionClient.getContratacionById(proyecto.getContratacionId());
        if (contratacion == null || contratacion.isEmpty()) {
            throw new RuntimeException("Contratación no encontrada");
        }

        return repository.save(proyecto);
    }

    public Proyecto obtenerProyectoPorId(Long idProyecto) {
        return repository.findById(idProyecto).orElse(null);
    }

    public List<Proyecto> listarProyectos() {
        return repository.findAll();
    }

    public Proyecto actualizarProyecto(Long idProyecto, Proyecto proyecto) {
        proyecto.setIdProyecto(idProyecto);
        return repository.save(proyecto);
    }

    public void eliminarProyecto(Long idProyecto) {
        repository.deleteById(idProyecto);
    }
}
