package org.example.menus;

import org.example.models.Horario;
import org.example.models.Tutoria;
import org.example.service.AdministracionTutoriaServece;
import org.example.models.Usuario;

import java.util.List;
import java.util.Scanner;

public class GestionDeMaestro {

    private final AdministracionTutoriaServece administracionTutoriaService;
    private final Usuario usuarioAutenticado;

    public GestionDeMaestro(AdministracionTutoriaServece administracionTutoriaService, Usuario usuarioAutenticado) {
        this.administracionTutoriaService = administracionTutoriaService;
        this.usuarioAutenticado = usuarioAutenticado;
    }

    //Case 2: Maestro
    public void visualizarTutorias() {
        System.out.println("\n--- Tutorías creadas por usted ---");
        List<Tutoria> tutorias = administracionTutoriaService.obtenerTutoriasDelMaestro(usuarioAutenticado.getIdUsuario());
        if (tutorias.isEmpty()) {
            System.out.println("No ha creado ninguna tutoría.");
        } else {
            for (Tutoria tutoria : tutorias) {
                System.out.println("ID: " + tutoria.getIdTutorias() + ", Nombre: " + tutoria.getNombreTutorias() + ", Estado: " + tutoria.getEstado());
            }
        }
    }

    // Case 3: Maestro
    public void listaDeEstudiantes() {
        System.out.println("Lista de estudiantes (implementación pendiente).");
    }
    // Case 4: Maestro
    public void cambiarHorario(Scanner scanner) {
        System.out.println("\n--- Cambiar Horario de Tutoría ---");
        System.out.print("Ingrese el ID de la tutoría: ");
        int tutoriaId = scanner.nextInt();
        scanner.nextLine();

        String resultado = administracionTutoriaService.actualizarHorarioTutoria(tutoriaId, usuarioAutenticado.getIdUsuario(), scanner);
        System.out.println(resultado);
    }

    //Case 6: Maestro
    public void verCalendario() {
        System.out.println("\n--- Calendario de Tutorías ---");
        List<Tutoria> tutorias = administracionTutoriaService.obtenerTutoriasDelMaestro(usuarioAutenticado.getIdUsuario());
        if (tutorias.isEmpty()) {
            System.out.println("No hay tutorías creadas.");
        } else {
            for (Tutoria tutoria : tutorias) {
                System.out.println("Tutoría: " + tutoria.getNombreTutorias());
                if (!tutoria.getHorarios().isEmpty()) {
                    for (Horario horario : tutoria.getHorarios()) {
                        System.out.println("  Día: " + horario.getDiaSemana() + " | Inicio: " + horario.getHoraInicio() + " | Fin: " + horario.getHoraFin());
                    }
                } else {
                    System.out.println("  Sin horarios asignados.");
                }
            }
        }
    }

}