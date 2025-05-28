package com.example.proyecto.controller;

import com.example.Proyecto.model.Proyecto;
import com.example.Proyecto.service.ProyectosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proyectos")
public class ProyectoController {

    @Autowired
    private ProyectosService proyectosService;

    @GetMapping
    public ResponseEntity<List<Proyecto>> obtenerProyectos() {
        List<Proyecto> proyectos = proyectosService.getProyectos();
        return proyectos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(proyectos);
    }

    @PostMapping
    public ResponseEntity<?> crearProyecto(@RequestBody Proyecto nuevoProyecto) {
        if (nuevoProyecto.getComentario().length() < 1 || nuevoProyecto.getComentario().length() > 100) {
            return ResponseEntity.badRequest().body("El comentario debe tener entre 1 y 100 caracteres.");
        }
        try {
            Proyecto creado = proyectosService.saveProyecto(nuevoProyecto);
            return ResponseEntity.status(201).body(creado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proyecto> obtenerProyectoPorId(@PathVariable Long id) {
        try {
            Proyecto proyecto = proyectosService.getProyectoPorId(id);
            return ResponseEntity.ok(proyecto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarProyecto(@PathVariable Long id, @RequestBody Proyecto actualizado) {
        try {
            Proyecto proyecto = proyectosService.actualizarProyecto(id, actualizado);
            return ResponseEntity.ok(proyecto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProyecto(@PathVariable Long id) {
        try {
            proyectosService.eliminarProyecto(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
