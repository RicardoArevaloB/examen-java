package com.skeletonhexa.adapter.ui;

import com.skeletonhexa.application.usecase.MedicoUsecase;
import com.skeletonhexa.domain.entities.Medico;
import java.util.List;
import java.util.Scanner;

public class MedicoUi {
    private MedicoUsecase service;
    private Scanner sc;

    public MedicoUi(MedicoUsecase s, Scanner sc) {
        this.service = s;
        this.sc = sc;
    }

    public void gestionMedico() {
        int op;
        do {
            System.out.println("\n--- DOCTORES ---");
            System.out.println("1. Añadir doctor");
            System.out.println("2. Ver doctores");
            System.out.println("3. Buscar doctor");
            System.out.println("4. Editar doctor");
            System.out.println("5. Borrar doctor");
            System.out.println("6. Doctores por especialidad");
            System.out.println("7. Salir");
            System.out.print("Elige: ");

            op = getNumber();

            switch (op) {
                case 1:
                    add();

                    break;
                case 2:
                    showAll();
                    break;
                case 3:
                    find();
                    break;
                case 4:
                    edit();
                    break;
                case 5:
                    delete();
                    break;
                case 6:
                    bySpecialty();
                    break;
                case 7:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while (op != 7);
    }

    private void add() {
        System.out.println("\nNUEVO DOCTOR");
        Medico m = new Medico();

        System.out.print("Nombre: ");
        m.setNombre(sc.nextLine());

        System.out.print("Especialidad (ID): ");
        m.setEspecialidadId(getNumber());

        System.out.print("Hora inicio (ej: 08:00): ");
        m.setHorarioInicio(sc.nextLine());

        System.out.print("Hora fin (ej: 16:00): ");
        m.setHorarioFin(sc.nextLine());

        try {
            m = service.registrarMedico(m);
            System.out.println("Doctor añadido! ID: " + m.getId());
        } catch (Exception e) {
            System.out.println("Ups! " + e.getMessage());
        }
    }

    private void showAll() {
        System.out.println("\nTODOS LOS DOCTORES");
        try {
            List<Medico> docs = service.listarTodosMedicos();
            if (docs.isEmpty()) {
                System.out.println("No hay doctores :(");
                return;
            }
            for (Medico d : docs) {
                System.out.println(d.getId() + " | " + d.getNombre() +
                        " | Especialidad: " + d.getEspecialidadId() +
                        " | Horas: " + d.getHorarioInicio() + " a " + d.getHorarioFin());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void find() {
        System.out.print("\nID del doctor: ");
        int id = getNumber();

        try {
            var doc = service.buscarPorId(id);
            if (doc.isPresent()) {
                Medico m = doc.get();
                System.out.println("\nNombre: " + m.getNombre());
                System.out.println("Especialidad: " + m.getEspecialidadId());
                System.out.println("Horario: " + m.getHorarioInicio() + " - " + m.getHorarioFin());
            } else {
                System.out.println("No existe ese doctor");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void edit() {
        System.out.print("\nID del doctor a editar: ");
        int id = getNumber();

        try {
            var doc = service.buscarPorId(id);
            if (doc.isEmpty()) {
                System.out.println("Doctor no encontrado");
                return;
            }

            Medico m = doc.get();
            System.out.println("\nEditando a: " + m.getNombre());

            System.out.print("Nuevo nombre (" + m.getNombre() + "): ");
            String nom = sc.nextLine();
            if (!nom.isEmpty())
                m.setNombre(nom);

            System.out.print("Nueva especialidad (" + m.getEspecialidadId() + "): ");
            String esp = sc.nextLine();
            if (!esp.isEmpty())
                m.setEspecialidadId(Integer.parseInt(esp));

            System.out.print("Nuevo horario inicio (" + m.getHorarioInicio() + "): ");
            String ini = sc.nextLine();
            if (!ini.isEmpty())
                m.setHorarioInicio(ini);

            System.out.print("Nuevo horario fin (" + m.getHorarioFin() + "): ");
            String fin = sc.nextLine();
            if (!fin.isEmpty())
                m.setHorarioFin(fin);

            service.actualizarMedico(m);
            System.out.println("Doctor actualizado!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void delete() {
        System.out.print("\nID del doctor a borrar: ");
        int id = getNumber();

        try {
            if (service.eliminarMedico(id)) {
                System.out.println("Borrado!");
            } else {
                System.out.println("No se pudo borrar");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void bySpecialty() {
        System.out.print("\nID de especialidad: ");
        int espId = getNumber();

        try {
            List<Medico> docs = service.buscarPorEspecialidad(espId);
            if (docs.isEmpty()) {
                System.out.println("No hay doctores con esa especialidad");
                return;
            }
            System.out.println("\nDoctores encontrados:");
            for (Medico d : docs) {
                System.out.println(d.getId() + " | " + d.getNombre() +
                        " | Horario: " + d.getHorarioInicio() + " a " + d.getHorarioFin());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private int getNumber() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.print("Necesito un número: ");
            }
        }
    }
}