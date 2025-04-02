package com.skeletonhexa.adapter.ui;

import com.skeletonhexa.application.usecase.EspecialidadUsecase;
import com.skeletonhexa.domain.entities.Especialidadmedic;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class EspecialidadUi {
    private final EspecialidadUsecase especialidadUsecase;
    private final Scanner scanner;

    public EspecialidadUi(EspecialidadUsecase especialidadUsecase, Scanner scanner) {
        this.especialidadUsecase = especialidadUsecase;
        this.scanner = scanner;
    }

    public void gestionEspecialidad() {
        int opcion;
        do {
            System.out.println("\n=== GESTIÓN DE ESPECIALIDADES MÉDICAS ===");
            System.out.println("1. Registrar nueva especialidad");
            System.out.println("2. Listar todas las especialidades");
            System.out.println("3. Actualizar especialidad");
            System.out.println("4. Eliminar especialidad");
            System.out.println("5. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            
            opcion = leerEntero();
            
            switch (opcion) {
                case 1:
                    registrarEspecialidad();
                    break;
                case 2:
                    listarEspecialidades();
                    break;
                case 3:
                    actualizarEspecialidad();
                    break;
                case 4:
                    eliminarEspecialidad();
                    break;
                case 5:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while (opcion != 5);
    }

    private void registrarEspecialidad() {
        System.out.println("\n--- REGISTRAR NUEVA ESPECIALIDAD ---");
        System.out.print("Nombre de la especialidad: ");
        String nombre = scanner.nextLine();

        Especialidadmedic nuevaEspecialidad = new Especialidadmedic();
        nuevaEspecialidad.setNombre(nombre);

        try {
            Especialidadmedic especialidadGuardada = especialidadUsecase.registrarEspecialidad(nuevaEspecialidad);
            System.out.println("\nEspecialidad registrada exitosamente:");
            System.out.println("ID: " + especialidadGuardada.getId());
            System.out.println("Nombre: " + especialidadGuardada.getNombre());
        } catch (Exception e) {
            System.out.println("\nEs " + e.getMessage());
        }
    }

    private void listarEspecialidades() {
        System.out.println("\n--- LISTADO DE ESPECIALIDADES ---");
        try {
            List<Especialidadmedic> especialidades = especialidadUsecase.listarTodasEspecialidades();
            
            if (especialidades.isEmpty()) {
                System.out.println("No hay especialidades registradas.");
                return;
            }
            
            System.out.printf("%-5s %-30s\n", "ID", "NOMBRE");
            for (Especialidadmedic esp : especialidades) {
                System.out.printf("%-5d %-30s\n", esp.getId(), esp.getNombre());
            }
        } catch (Exception e) {
            System.out.println("Error al listar especialidades: " + e.getMessage());
        }
    }

    private void actualizarEspecialidad() {
        System.out.println("\n--- ACTUALIZAR ESPECIALIDAD ---");
        System.out.print("Ingrese el ID de la especialidad a actualizar: ");
        int id = leerEntero();

        try {
            Optional<Especialidadmedic> especialidadExistente = especialidadUsecase.buscarPorId(id);
            
            if (!especialidadExistente.isPresent()) {
                System.out.println("\nNo se encontró una especialidad con el ID proporcionado.");
                return;
            }
            
            System.out.println("\nDatos actuales:");
            System.out.println("Nombre: " + especialidadExistente.get().getNombre());
            
            System.out.print("\nNuevo nombre (dejar vacío para mantener el actual): ");
            String nuevoNombre = scanner.nextLine();
            
            Especialidadmedic especialidadActualizar = especialidadExistente.get();
            if (!nuevoNombre.isEmpty()) {
                especialidadActualizar.setNombre(nuevoNombre);
            }
            
            Especialidadmedic especialidadActualizada = especialidadUsecase.actualizarEspecialidad(especialidadActualizar);
            System.out.println("\nEspecialidad actualizada exitosamente:");
            System.out.println("ID: " + especialidadActualizada.getId());
            System.out.println("Nombre: " + especialidadActualizada.getNombre());
        } catch (Exception e) {
            System.out.println("Error al actualizar especialidad: " + e.getMessage());
        }
    }

    private void eliminarEspecialidad() {
        System.out.println("\n--- ELIMINAR ESPECIALIDAD ---");
        System.out.print("Ingrese el ID de la especialidad a eliminar: ");
        int id = leerEntero();

        try {
            boolean eliminada = especialidadUsecase.eliminarEspecialidad(id);
            if (eliminada) {
                System.out.println("\nEspecialidad eliminada exitosamente.");
            } else {
                System.out.println("\nNo se pudo eliminar la especialidad. Verifique el ID.");
            }
        } catch (Exception e) {
            System.out.println("Error al eliminar especialidad: " + e.getMessage());
        }
    }

    private int leerEntero() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Entrada inválida. Ingrese un número: ");
            }
        }
    }
}

