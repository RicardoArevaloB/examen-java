package com.skeletonhexa.adapter.ui;

import com.skeletonhexa.application.usecase.CitaUsecase;
import com.skeletonhexa.domain.entities.Cita;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class CitaUi {
    private final CitaUsecase citaService;
    private final Scanner sc;

    public CitaUi(CitaUsecase service, Scanner sc) {
        this.citaService = service;
        this.sc = sc;
    }

    public void gestioncitas() {
        int op;
        do {
            System.out.println("\n=== MENU CITAS ===");
            System.out.println("1. Agendar cita");
            System.out.println("2. Ver todas");
            System.out.println("3. Buscar por ID");
            System.out.println("4. Cambiar cita");
            System.out.println("5. Cancelar cita");
            System.out.println("6. Completar cita");
            System.out.println("7. Ver citas de paciente");
            System.out.println("8. Ver citas de doctor");
            System.out.println("9. Salir");
            System.out.print("Elige: ");

            op = leerNumero();

            switch (op) {
                case 1:
                    agendar();
                    break;
                case 2:
                    verTodas();
                    break;
                case 3:
                    buscar();
                    break;
                case 4:
                    cambiar();
                    break;
                case 5:
                    cancelar();
                    break;
                case 6:
                    completar();
                    break;
                case 7:
                    porPaciente();
                    break;
                case 8:
                    porDoctor();
                    break;
                case 9:
                    System.out.println("Chao!");
                    break;
                default:
                    System.out.println("Opcion mala!");
            }
        } while (op != 9);
    }

    private void agendar() {
        System.out.println("\nNUEVA CITA");
        Cita c = new Cita();

        System.out.print("ID Paciente: ");
        c.setPacienteId(leerNumero());

        System.out.print("ID Doctor: ");
        c.setMedicoId(leerNumero());

        System.out.print("Fecha y hora (aaaa-mm-dd hh:mm): ");
        c.setFechaHora(leerFechaHora());

        c.setEstado("pendiente");

        try {
            c = citaService.programarCita(c);
            System.out.println("Cita agendada! ID: " + c.getId());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void verTodas() {
        System.out.println("\nTODAS LAS CITAS");
        try {
            List<Cita> citas = citaService.listarTodasCitas();

            if (citas.isEmpty()) {
                System.out.println("No hay citas");
                return;
            }

            for (Cita c : citas) {
                System.out.println(c.getId() + " | Paciente: " + c.getPacienteId() +
                        " | Doctor: " + c.getMedicoId() +
                        " | Fecha: " + c.getFechaHora().toString().replace("T", " ") +
                        " | Estado: " + c.getEstado());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void buscar() {
        System.out.print("\nID de cita: ");
        int id = leerNumero();

        try {
            var cita = citaService.buscarPorId(id);
            if (cita.isPresent()) {
                mostrarCita(cita.get());
            } else {
                System.out.println("No existe");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void cambiar() {
        System.out.print("\nID de cita a cambiar: ");
        int id = leerNumero();

        try {
            var cita = citaService.buscarPorId(id);
            if (cita.isEmpty()) {
                System.out.println("No existe");
                return;
            }

            Cita c = cita.get();
            System.out.println("Editando cita ID: " + c.getId());

            System.out.print("Nuevo ID Paciente (" + c.getPacienteId() + "): ");
            String pac = sc.nextLine();
            if (!pac.isEmpty())
                c.setPacienteId(Integer.parseInt(pac));

            System.out.print("Nuevo ID Doctor (" + c.getMedicoId() + "): ");
            String doc = sc.nextLine();
            if (!doc.isEmpty())
                c.setMedicoId(Integer.parseInt(doc));

            System.out.print("Nueva fecha (" + c.getFechaHora().toString().replace("T", " ") + "): ");
            String fecha = sc.nextLine();
            if (!fecha.isEmpty())
                c.setFechaHora(LocalDateTime.parse(fecha.replace(" ", "T")));

            citaService.actualizarCita(c);
            System.out.println("Cita cambiada!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void cancelar() {
        System.out.print("\nID de cita a cancelar: ");
        int id = leerNumero();

        try {
            if (citaService.cancelarCita(id)) {
                System.out.println("Cancelada!");
            } else {
                System.out.println("No se pudo cancelar");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void completar() {
        System.out.print("\nID de cita a completar: ");
        int id = leerNumero();

        try {
            if (citaService.completarCita(id)) {
                System.out.println("Completada!");
            } else {
                System.out.println("No se pudo completar");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void porPaciente() {
        System.out.print("\nID de paciente: ");
        int id = leerNumero();

        try {
            List<Cita> citas = citaService.buscarCitasPorPaciente(id);

            if (citas.isEmpty()) {
                System.out.println("No hay citas");
                return;
            }

            System.out.println("\nCitas encontradas:");
            for (Cita c : citas) {
                System.out.println("ID: " + c.getId() +
                        " | Doctor: " + c.getMedicoId() +
                        " | Fecha: " + c.getFechaHora().toString().replace("T", " ") +
                        " | Estado: " + c.getEstado());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void porDoctor() {
        System.out.print("\nID de doctor: ");
        int id = leerNumero();

        try {
            List<Cita> citas = citaService.buscarCitasPorMedico(id);

            if (citas.isEmpty()) {
                System.out.println("No hay citas");
                return;
            }

            System.out.println("\nCitas encontradas:");
            for (Cita c : citas) {
                System.out.println("ID: " + c.getId() +
                        " | Paciente: " + c.getPacienteId() +
                        " | Fecha: " + c.getFechaHora().toString().replace("T", " ") +
                        " | Estado: " + c.getEstado());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void mostrarCita(Cita c) {
        System.out.println("\nID: " + c.getId());
        System.out.println("Paciente: " + c.getPacienteId());
        System.out.println("Doctor: " + c.getMedicoId());
        System.out.println("Fecha: " + c.getFechaHora().toString().replace("T", " "));
        System.out.println("Estado: " + c.getEstado());
    }

    private LocalDateTime leerFechaHora() {
        while (true) {
            try {
                return LocalDateTime.parse(sc.nextLine().replace(" ", "T"));
            } catch (Exception e) {
                System.out.print("Formato malo. Usa aaaa-mm-dd hh:mm: ");
            }
        }
    }

    private int leerNumero() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.print("Necesito un numero: ");
            }
        }
    }
}