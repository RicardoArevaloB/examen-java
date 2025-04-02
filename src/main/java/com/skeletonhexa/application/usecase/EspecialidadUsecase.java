package com.skeletonhexa.application.usecase;

import java.util.List;
import java.util.Optional;

import com.skeletonhexa.domain.entities.Especialidadmedic;
import com.skeletonhexa.domain.repository.especialidadrepository;


public class EspecialidadUsecase {
    private final especialidadrepository especialidadRepository;

    public EspecialidadUsecase(especialidadrepository especialidadRepository) {
        this.especialidadRepository = especialidadRepository;
    }

    public Especialidadmedic registrarEspecialidad(Especialidadmedic especialidad) {
        if (especialidad.getNombre() == null || especialidad.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("No se pueden registrar espacios en blanco");
        }
        return especialidadRepository.save(especialidad);
    }

    public List<Especialidadmedic> listarTodasEspecialidades() {
        return especialidadRepository.findAll();
    }

    public Optional<Especialidadmedic> buscarPorId(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID de especialidad inválido");
        }
        return especialidadRepository.findById(id);
    }

    public Especialidadmedic actualizarEspecialidad(Especialidadmedic especialidad) {
        if (especialidad.getId() == null || especialidad.getId() <= 0) {
            throw new IllegalArgumentException("ID de especialidad inválido");
        }
        if (especialidad.getNombre() == null || especialidad.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la especialidad es requerido");
        }
        
        return especialidadRepository.update(especialidad);
    }

    public boolean eliminarEspecialidad(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID de especialidad inválido");
        }
        return especialidadRepository.delete(id);
    }
}