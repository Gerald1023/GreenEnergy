package com.example.proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Proyecto.model.Proyecto;

@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Long>{

}

