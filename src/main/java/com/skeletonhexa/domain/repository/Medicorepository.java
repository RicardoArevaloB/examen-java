package com.skeletonhexa.domain.repository;

import java.util.List;
import java.util.Optional;
import com.skeletonhexa.domain.entities.Medico;

public interface Medicorepository {
    Medico save(Medico medico);
    Optional<Medico> findById(Integer id);
    List<Medico> findAll();
    boolean delete(Integer id);
    Medico update(Medico medico);
    List<Medico> findByEspecialidad(Integer especialidadId);
}