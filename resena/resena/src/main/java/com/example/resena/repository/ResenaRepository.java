package com.example.resena.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.resena.model.Resena;

@Repository
public interface ResenaRepository extends JpaRepository<Resena, Long>{

}
