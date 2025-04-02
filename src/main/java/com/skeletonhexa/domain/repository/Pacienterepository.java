package com.skeletonhexa.domain.repository;

import java.util.List;
import java.util.Optional;
import com.skeletonhexa.domain.entities.Paciente;

public interface Pacienterepository {
    Paciente save(Paciente paciente);
    Optional<Paciente> findById(Integer id);
    List<Paciente> findAll();
    boolean delete(Integer id);
    Paciente update(Paciente paciente);
}
