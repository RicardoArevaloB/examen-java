package com.skeletonhexa.adapter.ui;

import com.skeletonhexa.application.usecase.PacienteUsecase;
import com.skeletonhexa.domain.entities.Paciente;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class PacienteUi {
    private final PacienteUsecase pacienteService;
    private final Scanner sc;

    public PacienteUi(PacienteUsecase service, Scanner sc) {
        this.pacienteService = service;
        this.sc = sc;
    }

    public void gestionPacientes() {
        int op;
        do {
            System.out.println("\n=== MENU PACIENTES ===");
            System.out.println("1. Nuevo paciente");
            System.out.println("2. Ver pacientes");
            System.out.println("3. Buscar paciente");
            System.out.println("4. Editar paciente");
            System.out.println("5. Borrar paciente");
            System.out.println("6. Salir");
            System.out.print("Elige: ");

            op = leerNumero();

            switch (op) {
                case 1:
                    agregar();
                    break;
                case 2:
                    listar();
                    break;
                case 3:
                    buscar();
                    break;
                case 4:
                    editar();
                    break;
                case 5:
                    borrar();
                    break;
                case 6:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opcion no valida!");
            }
        } while (op != 6);
    }

    private void agregar() {
        System.out.println("\nNUEVO PACIENTE");
        Paciente p = new Paciente();

        System.out.print("Nombre: ");
        p.setNombre(sc.nextLine());

        System.out.print("Apellido: ");
        p.setApellido(sc.nextLine());

        System.out.print("Fecha nacimiento (aaaa-mm-dd): ");
        p.setFechaNacimiento(leerFecha());

        System.out.print("Direccion: ");
        p.setDireccion(sc.nextLine());

        System.out.print("Telefono: ");
        p.setTelefono(sc.nextLine());

        System.out.print("Email: ");
        p.setEmail(sc.nextLine());

        try {
            p = pacienteService.registrarPaciente(p);
            System.out.println("Paciente creado! ID: " + p.getId());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void listar() {
        System.out.println("\nLISTA DE PACIENTES");
        try {
            List<Paciente> pacientes = pacienteService.listarTodosPacientes();

            if (pacientes.isEmpty()) {
                System.out.println("No hay pacientes");
                return;
            }

            for (Paciente p : pacientes) {
                System.out.println(p.getId() + " | " + p.getNombre() + " " + p.getApellido() +
                        " | " + p.getFechaNacimiento() + " | " + p.getTelefono());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void buscar() {
        System.out.print("\nID del paciente: ");
        int id = leerNumero();

        try {
            var paciente = pacienteService.buscarPorId(id);
            if (paciente.isPresent()) {
                mostrarPaciente(paciente.get());
            } else {
                System.out.println("No encontrado");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void editar() {
        System.out.print("\nID del paciente a editar: ");
        int id = leerNumero();

        try {
            var paciente = pacienteService.buscarPorId(id);
            if (paciente.isEmpty()) {
                System.out.println("No existe");
                return;
            }

            Paciente p = paciente.get();
            System.out.println("Editando: " + p.getNombre());

            System.out.print("Nuevo nombre (" + p.getNombre() + "): ");
            String nom = sc.nextLine();
            if (!nom.isEmpty())
                p.setNombre(nom);

            System.out.print("Nuevo apellido (" + p.getApellido() + "): ");
            String ape = sc.nextLine();
            if (!ape.isEmpty())
                p.setApellido(ape);

            System.out.print("Nueva fecha (" + p.getFechaNacimiento() + "): ");
            String fecha = sc.nextLine();
            if (!fecha.isEmpty())
                p.setFechaNacimiento(LocalDate.parse(fecha));

            System.out.print("Nueva direccion (" + p.getDireccion() + "): ");
            String dir = sc.nextLine();
            if (!dir.isEmpty())
                p.setDireccion(dir);

            System.out.print("Nuevo telefono (" + p.getTelefono() + "): ");
            String tel = sc.nextLine();
            if (!tel.isEmpty())
                p.setTelefono(tel);

            System.out.print("Nuevo email (" + p.getEmail() + "): ");
            String email = sc.nextLine();
            if (!email.isEmpty())
                p.setEmail(email);

            pacienteService.actualizarPaciente(p);
            System.out.println("Actualizado!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void borrar() {
        System.out.print("\nID del paciente a borrar: ");
        int id = leerNumero();

        try {
            if (pacienteService.eliminarPaciente(id)) {
                System.out.println("Borrado!");
            } else {
                System.out.println("No se pudo borrar");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void mostrarPaciente(Paciente p) {
        System.out.println("\nID: " + p.getId());
        System.out.println("Nombre: " + p.getNombre() + " " + p.getApellido());
        System.out.println("Nacimiento: " + p.getFechaNacimiento());
        System.out.println("Direccion: " + p.getDireccion());
        System.out.println("Telefono: " + p.getTelefono());
        System.out.println("Email: " + p.getEmail());
    }

    private LocalDate leerFecha() {
        while (true) {
            try {
                return LocalDate.parse(sc.nextLine());
            } catch (Exception e) {
                System.out.print("Fecha invalida. Usa aaaa-mm-dd: ");
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