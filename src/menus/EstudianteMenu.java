package org.example.menus;

import org.example.service.AuthService;

import java.util.List;
import java.util.Scanner;

public class EstudianteMenu {
    private final AuthService authService;
    private final GestionEstudiante gestionEstudiante;

    public EstudianteMenu(AuthService authService) {
        this.authService = authService;
        this.gestionEstudiante = new GestionEstudiante();

    }

    public void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n--- Menú Estudiante ---");
            System.out.println("1. Ver todas las tutorías");
            System.out.println("2. Ver todas las materias (mantenimiento pendiente)");
            System.out.println("3. Inscribirse a una tutoría");
            System.out.println("4. Salir de una tutoría");
            System.out.println("5. Mostrar calendario de tutorías");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");
            System.out.println();
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    gestionEstudiante.verTodasLasTutorias();
                    break;
                case 2:
                    gestionEstudiante.verMaterias();
                    //Funcionalidad de ver materias aún no implementada
                    break;
                case 3:
                    gestionEstudiante.inscribirEnTutoria(scanner, authService);
                    break;
                case 4:
                    gestionEstudiante.salirDeTutoria(scanner, authService);
                    break;
                case 5:
                    gestionEstudiante.mostrarCalendario();
                    break;
                case 6:
                    System.out.println("Saliendo del menú estudiante y cerrando la aplicación...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 6);
        System.out.println("-----------------------------------");
    }
}