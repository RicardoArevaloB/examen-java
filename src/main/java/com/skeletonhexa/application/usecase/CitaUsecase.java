package com.skeletonhexa.application.usecase;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import com.skeletonhexa.domain.entities.Cita;
import com.skeletonhexa.domain.repository.Citarepository;

public class CitaUsecase {
    private final Citarepository citaRepository;

    public CitaUsecase(Citarepository citaRepository) {
        this.citaRepository = citaRepository;
    }

    public Cita programarCita(Cita cita) {
        if (cita.getPacienteId() == null || cita.getPacienteId() <= 0) {
            throw new IllegalArgumentException("ID de paciente inválido");
        }
        if (cita.getMedicoId() == null || cita.getMedicoId() <= 0) {
            throw new IllegalArgumentException("ID de médico inválido");
        }
        if (cita.getFechaHora() == null || cita.getFechaHora().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Fecha y hora de la cita inválida");
        }
        return citaRepository.save(cita);
    }

    public List<Cita> listarTodasCitas() {
        return citaRepository.findAll();
    }

    public Optional<Cita> buscarPorId(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID de cita inválido");
        }
        return citaRepository.findById(id);
    }

    public Cita actualizarCita(Cita cita) {
        if (cita.getId() == null || cita.getId() <= 0) {
            throw new IllegalArgumentException("ID de cita inválido");
        }
        if (cita.getPacienteId() == null || cita.getPacienteId() <= 0) {
            throw new IllegalArgumentException("ID de paciente inválido");
        }
        if (cita.getMedicoId() == null || cita.getMedicoId() <= 0) {
            throw new IllegalArgumentException("ID de médico inválido");
        }
        if (cita.getFechaHora() == null || cita.getFechaHora().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Fecha y hora de la cita inválida");
        }
        return citaRepository.update(cita);
    }

    public boolean cancelarCita(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID de cita inválido");
        }
        Optional<Cita> citaOpt = citaRepository.findById(id);
        if (!citaOpt.isPresent()) {
            throw new IllegalArgumentException("Cita no encontrada");
        }
        Cita cita = citaOpt.get();
        cita.setEstado("cancelada");
        citaRepository.update(cita);
        return true;
    }

    public boolean completarCita(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID de cita inválido");
        }
        Optional<Cita> citaOpt = citaRepository.findById(id);
        if (!citaOpt.isPresent()) {
            throw new IllegalArgumentException("Cita no encontrada");
        }
        Cita cita = citaOpt.get();
        cita.setEstado("completada");
        citaRepository.update(cita);
        return true;
    }

    public List<Cita> buscarCitasPorPaciente(Integer pacienteId) {
        if (pacienteId == null || pacienteId <= 0) {
            throw new IllegalArgumentException("ID de paciente inválido");
        }
        return citaRepository.findByPaciente(pacienteId);
    }

    public List<Cita> buscarCitasPorMedico(Integer medicoId) {
        if (medicoId == null || medicoId <= 0) {
            throw new IllegalArgumentException("ID de médico inválido");
        }
        return citaRepository.findByMedico(medicoId);
    }
}