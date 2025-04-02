package com.skeletonhexa.domain.repository;

import java.util.List;
import java.util.Optional;
import com.skeletonhexa.domain.entities.Cita;

public interface Citarepository {
    Cita save(Cita cita);
    Optional<Cita> findById(Integer id);
    List<Cita> findAll();
    boolean delete(Integer id);
    Cita update(Cita cita);
    List<Cita> findByPaciente(Integer pacienteId);
    List<Cita> findByMedico(Integer medicoId);
    List<Cita> findByEstado(String estado);
}
