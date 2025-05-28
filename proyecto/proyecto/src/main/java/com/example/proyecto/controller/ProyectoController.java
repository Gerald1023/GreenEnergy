package com.example.proyecto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Proyecto.model.Proyecto;
import com.example.Proyecto.service.ProyectosService;
import com.example.Proyecto.webclient.ClienteClient;
import com.example.Proyecto.webclient.ContratacionClient;
import com.example.Proyecto.webclient.UsuarioClient;

@RestController
@RequestMapping("/api/proyecto")
public class ProyectoController {
    @Autowired
    private ProyectosService proyectosService;

    @Autowired
    ClienteClient clienteClient;

    @Autowired
    UsuarioClient usuarioClient;

    @Autowired
    ContratacionClient contratacionClient;

    @GetMapping
    public ResponseEntity<List<Proyecto>> obtenerProyectos() {
        List<Proyecto> proyecto = proyectosService.getProyectos();
        if (proyecto.isEmpty()) {
            
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(proyecto);
    }

    
    @PostMapping
    public ResponseEntity<?> crearProyecto(@RequestBody Proyecto nuevoproyecto) {
        if (nuevoproyecto.getComentario().length() < 1|| nuevoproyecto.getComentario().length() > 100) {
            return ResponseEntity.badRequest().body("El comentario debe tener entre 1 y 100 caracteres.");
        }
        try {
            Proyecto proyecto = proyectosService.saveProyecto(nuevoproyecto);
            return ResponseEntity.status(201).body(proyecto);
        } catch (RuntimeException e) {
            
            return ResponseEntity.status(404).body(e.getMessage());
        } 
    }

    //buscar un estado mediante su id
    @GetMapping("/{id}")
    public ResponseEntity<Proyecto> obtenerProyectoPorId(@PathVariable Long id) {
        try {
            // verificar si existe el estado
            Proyecto proyecto = proyectosService.getProyectoPorId(id);
            return ResponseEntity.ok(proyecto);
        } catch (Exception e) {
            // retorno codigo 404
            return ResponseEntity.notFound().build();
        }

    }

    //para actualizar un proyecto
    @PutMapping ("/{id}")
    public ResponseEntity<?> actualizarProyecto(@PathVariable Long id, @RequestBody Proyecto proyectoActualizado) {
        try {
            //verificar si existe el proyecto
            Proyecto proyecto = proyectosService.getProyectoPorId(id);
            //actu el proyecto
            proyecto.setComentario(proyectoActualizado.getComentario());
            proyecto.setEstadoId(proyectoActualizado.getEstadoId());
            //guardar el proyecto actualizado
            proyectosService.saveProyecto(proyecto);
            return ResponseEntity.ok(proyecto);
        } catch (Exception e) {
            //retorno codigo 404
            return ResponseEntity.notFound().build();
        }
    }
}