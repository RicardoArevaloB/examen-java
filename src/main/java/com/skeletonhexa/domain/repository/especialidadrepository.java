package com.skeletonhexa.domain.repository;

import java.util.List;
import java.util.Optional;

import com.skeletonhexa.domain.entities.Especialidadmedic;

public interface especialidadrepository {
    Especialidadmedic save(Especialidadmedic especialidad);
    Optional<Especialidadmedic> findById(Integer id);
    List<Especialidadmedic> findAll();
    boolean delete(Integer id);
    Especialidadmedic update(Especialidadmedic especialidad);
}
