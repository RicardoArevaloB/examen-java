package com.skeletonhexa.application.usecase;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import com.skeletonhexa.domain.entities.Paciente;
import com.skeletonhexa.domain.repository.Pacienterepository;

public class PacienteUsecase {
    private final Pacienterepository pacienteRepository;

    public PacienteUsecase(Pacienterepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public Paciente registrarPaciente(Paciente paciente) {
        if (paciente.getNombre() == null || paciente.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del paciente es requerido");
        }
        if (paciente.getApellido() == null || paciente.getApellido().trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido del paciente es requerido");
        }
        if (paciente.getFechaNacimiento() == null || paciente.getFechaNacimiento().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de nacimiento es inválida");
        }
        return pacienteRepository.save(paciente);
    }

    public List<Paciente> listarTodosPacientes() {
        return pacienteRepository.findAll();
    }

    public Optional<Paciente> buscarPorId(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID de paciente inválido");
        }
        return pacienteRepository.findById(id);
    }

    public Paciente actualizarPaciente(Paciente paciente) {
        if (paciente.getId() == null || paciente.getId() <= 0) {
            throw new IllegalArgumentException("ID de paciente inválido");
        }
        if (paciente.getNombre() == null || paciente.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del paciente es requerido");
        }
        if (paciente.getApellido() == null || paciente.getApellido().trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido del paciente es requerido");
        }
        if (paciente.getFechaNacimiento() == null || paciente.getFechaNacimiento().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de nacimiento es inválida");
        }
        return pacienteRepository.update(paciente);
    }

    public boolean eliminarPaciente(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID de paciente inválido");
        }
        return pacienteRepository.delete(id);
    }
}
