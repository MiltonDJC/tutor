package org.example.menus;

import org.example.models.Horario;
import org.example.models.Tutoria;
import org.example.service.AuthService;
import org.example.service.EstudianteService;
import org.example.service.CalendarioService;
import org.example.service.EstudianteTutoriaService;

import java.util.List;
import java.util.Scanner;

public class GestionEstudiante {

    private final EstudianteService estudianteService;
    private final EstudianteTutoriaService estudianteTutoriaService;
    private final CalendarioService calendarioService;

    public GestionEstudiante() {
        this.estudianteService = new EstudianteService();
        this.estudianteTutoriaService = new EstudianteTutoriaService();
        this.calendarioService = new CalendarioService();
    }
    //Case 1: Estudiante
    public void verTodasLasTutorias() {
        System.out.println("Listando tutorías:");
        List<Tutoria> tutorias = estudianteService.listarTutorias();
        for (Tutoria tutoria : tutorias) {
            System.out.println(tutoria.getIdTutorias() + ": " + tutoria.getNombreTutorias());
            if (tutoria.getHorarios() != null && !tutoria.getHorarios().isEmpty()) {
                System.out.println("  Horarios:");
                for (Horario horario : tutoria.getHorarios()) {
                    System.out.println("    - " + horario);
                }
            } else {
                System.out.println("  No hay horarios disponibles.");
            }
        }
    }

    // Caso 2: Estudiante
    public void verMaterias() {
        System.out.println("Ver materias (implementación pendiente).");
    }

    // Caso 3: Estudiante
    public void inscribirEnTutoria(Scanner scanner, AuthService authService) {
        System.out.print("Ingrese el ID de la tutoría a la que desea unirse: ");
        int idTutoria = scanner.nextInt();
        int idEstudiante = authService.getIdUsuarioActual();
        if (idEstudiante != -1) {
            String resultado = estudianteTutoriaService.inscribirEnTutoria(idEstudiante, idTutoria);
            System.out.println(resultado);
        } else {
            System.out.println("No hay usuario autenticado.");
        }
    }

    // Caso 4: Estudiante
    public void salirDeTutoria(Scanner scanner, AuthService authService) {
        System.out.print("Ingrese el ID de la tutoría de la que desea salir: ");
        int idTutoria = scanner.nextInt();
        int idEstudiante = authService.getIdUsuarioActual();
        if (idEstudiante != -1) {
            String resultado = estudianteTutoriaService.salirDeTutoria(idEstudiante, idTutoria);
            System.out.println(resultado);
        } else {
            System.out.println("No hay usuario autenticado.");
        }
    }

    // Caso 5: Estudiante
    public void mostrarCalendario() {
        System.out.println("Generando Calendario en Matriz:");
        List<Tutoria> listaTutorias = estudianteService.listarTutorias();
        calendarioService.mostrarCalendarioEnMatriz(listaTutorias);
    }
}