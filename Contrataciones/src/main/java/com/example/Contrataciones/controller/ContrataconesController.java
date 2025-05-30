package com.example.Contrataciones.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.example.Contrataciones.model.Contrataciones;
import com.example.Contrataciones.service.ContratacionesService;



@RestController
@RequestMapping("/api/v1/contrataciones")
public class ContrataconesController {
    @Autowired
    private ContratacionesService contratacionesService;

    //metodo para obtener todas las contrataciones
    @GetMapping("/all")
    public ResponseEntity<List<Contrataciones>> getAllContrataciones() {
        List<Contrataciones> contrataciones = contratacionesService.obtenerTodasContrataciones();
        if (contrataciones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(contrataciones);
    }

    //metodo para obtener una contratacion por id
    @GetMapping("/id/{id}")
    public ResponseEntity<Contrataciones> getContratacionById(@PathVariable Long id) {
        Contrataciones contratacion = contratacionesService.obtenercontratacionById(id);
        if (contratacion == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(contratacion);
    }
    //metodo para crear una contratacion
    @PostMapping("/nuevo")
    public ResponseEntity<Contrataciones> createContratacion(long id,Contrataciones contratacion) {
        Contrataciones nuevaContratacion = contratacionesService.actualizarContratacion(id,contratacion);
        return ResponseEntity.ok(nuevaContratacion);
    }

    //metodo para actualizar una contratacion
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Contrataciones> actualizarContratacion(@PathVariable Long id,@RequestBody Contrataciones contratacion) {
        Contrataciones existeContratacion = contratacionesService.obtenercontratacionById(id);
        try {
            Contrataciones actualizada = contratacionesService.actualizarContratacion(id, contratacion);
            return ResponseEntity.ok(actualizada);
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    //metodo para eliminar una contratacion
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarContratacion(@PathVariable Long id) {
        Contrataciones existeContratacion = contratacionesService.obtenercontratacionById(id);
        if (existeContratacion == null) {
            return ResponseEntity.notFound().build();
        }
        contratacionesService.eliminarContratacion(id);
        return ResponseEntity.noContent().build();
    }


}
