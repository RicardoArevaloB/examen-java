package com.skeletonhexa;

import java.util.Scanner;

import com.skeletonhexa.adapter.ui.CitaUi;
import com.skeletonhexa.adapter.ui.EspecialidadUi;
import com.skeletonhexa.adapter.ui.MedicoUi;
import com.skeletonhexa.adapter.ui.PacienteUi;
import com.skeletonhexa.application.usecase.CitaUsecase;
import com.skeletonhexa.application.usecase.EspecialidadUsecase;
import com.skeletonhexa.application.usecase.MedicoUsecase;
import com.skeletonhexa.application.usecase.PacienteUsecase;
import com.skeletonhexa.domain.repository.Citarepository;
import com.skeletonhexa.domain.repository.Medicorepository;
import com.skeletonhexa.domain.repository.Pacienterepository;
import com.skeletonhexa.domain.repository.especialidadrepository;
import com.skeletonhexa.infrastructure.database.ConnectMysqlFactory;
import com.skeletonhexa.infrastructure.persistence.CitaRepositoryImpl;
import com.skeletonhexa.infrastructure.persistence.EspecialidadRepositoryImpl;
import com.skeletonhexa.infrastructure.persistence.MedicoRepositoryImpl;
import com.skeletonhexa.infrastructure.persistence.PacienteRepositoryImpl;

public class Main {
    public static void main(String[] args) {
        especialidadrepository repository = new EspecialidadRepositoryImpl(ConnectMysqlFactory.crearConexion());
        Pacienterepository repositorypaciente = new PacienteRepositoryImpl(ConnectMysqlFactory.crearConexion());
        Medicorepository reposotorymedico = new MedicoRepositoryImpl(ConnectMysqlFactory.crearConexion());
        Citarepository repositorycita = new CitaRepositoryImpl(ConnectMysqlFactory.crearConexion());

        PacienteUsecase pacienteUsecase = new PacienteUsecase(repositorypaciente);
        EspecialidadUsecase especialidadcasodeuso = new EspecialidadUsecase(repository);
        MedicoUsecase medicicasodeuso = new MedicoUsecase(reposotorymedico);
        CitaUsecase citacasodeuso = new CitaUsecase(repositorycita);


        try(Scanner sc = new Scanner(System.in)){
            EspecialidadUi interfazEspecilidad = new EspecialidadUi(especialidadcasodeuso, sc);
            PacienteUi interfazPaciente = new PacienteUi(pacienteUsecase, sc);
            MedicoUi interfazmedico = new MedicoUi(medicicasodeuso, sc);
            CitaUi interfazcita = new CitaUi(citacasodeuso, sc);

            int option;
            do{
                System.out.println("************************");
                System.out.println("*    Menu pricipal     *");
                System.out.println("************************");
                System.out.println("\n1.Gestion especialidades");
                System.out.println("2.Gestion de citas");
                System.out.println("3.Gestion de medicos");
                System.out.println("4.Gestion de pacientes");
                System.out.println("5.Gestion Historiales de pacientes");
                System.out.println("6.Salir del programa");
                option = sc.nextInt();
                sc.nextLine();
                switch (option) {
                    case 1:
                            interfazEspecilidad.gestionEspecialidad();
                        break;

                    case 2:
                        interfazcita.gestioncitas();
                        break;
                    

                    case 3:
                        interfazmedico.gestionMedico();
                        break;

                    case 4:
                        interfazPaciente.gestionPacientes();
                        break;

                    case 5:
                        System.out.println("Gestion de Historiales de pacientes");
                        break;
                    case 6:
                        System.out.println("Saliendo del programa...");
                        break;
                    default:
                        System.out.println("Opcion no valida. intente de nuevo.");
                        
                }
            }while (option !=6);
                
            
        }catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }
        
    }
}