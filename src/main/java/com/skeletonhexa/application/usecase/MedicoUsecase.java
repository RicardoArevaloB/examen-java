package com.skeletonhexa.application.usecase;

import java.util.List;
import java.util.Optional;
import com.skeletonhexa.domain.entities.Medico;
import com.skeletonhexa.domain.repository.Medicorepository;

public class MedicoUsecase {
    private final Medicorepository medicoRepository;

    public MedicoUsecase(Medicorepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    public Medico registrarMedico(Medico medico) {
        if (medico.getNombre() == null || medico.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del médico es requerido");
        }
        if (medico.getEspecialidadId() == null || medico.getEspecialidadId() <= 0) {
            throw new IllegalArgumentException("La especialidad del médico es requerida");
        }
        if (medico.getHorarioInicio() == null || medico.getHorarioFin() == null) {
            throw new IllegalArgumentException("El horario del médico es requerido");
        }
        return medicoRepository.save(medico);
    }

    public List<Medico> listarTodosMedicos() {
        return medicoRepository.findAll();
    }

    public Optional<Medico> buscarPorId(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID de médico inválido");
        }
        return medicoRepository.findById(id);
    }

    public Medico actualizarMedico(Medico medico) {
        if (medico.getId() == null || medico.getId() <= 0) {
            throw new IllegalArgumentException("ID de médico inválido");
        }
        if (medico.getNombre() == null || medico.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del médico es requerido");
        }
        if (medico.getEspecialidadId() == null || medico.getEspecialidadId() <= 0) {
            throw new IllegalArgumentException("La especialidad del médico es requerida");
        }
        if (medico.getHorarioInicio() == null || medico.getHorarioFin() == null) {
            throw new IllegalArgumentException("El horario del médico es requerido");
        }
        return medicoRepository.update(medico);
    }

    public boolean eliminarMedico(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID de médico inválido");
        }
        return medicoRepository.delete(id);
    }

    public List<Medico> buscarPorEspecialidad(Integer especialidadId) {
        if (especialidadId == null || especialidadId <= 0) {
            throw new IllegalArgumentException("ID de especialidad inválido");
        }
        return medicoRepository.findByEspecialidad(especialidadId);
    }
}
