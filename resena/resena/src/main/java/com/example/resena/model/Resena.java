package com.example.resena.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="resena")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resena {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idResena;

    @Column(nullable = false,length = 100)
    private String comentario;
    
    @Column(nullable = false)
    private LocalDate fechaComentario;

    @Column(nullable = false)   
    private Long idUsuario;

    @Column(nullable = false)   
    private Long idServicio;

}
