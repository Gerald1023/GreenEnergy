package com.example.soporte.repository;

import com.example.soporte.model.Respuesta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {
    List<Respuesta> findByTicketId(Long ticketId);
}