package org.example.menus;

import org.example.models.Horario;
import org.example.models.Usuario;
import org.example.service.MaestroTutoriaService;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class GestorTutoriasMaestro {

    private final MaestroTutoriaService maestroTutoriaService;
    private final Usuario usuarioAutenticado;

    public GestorTutoriasMaestro(MaestroTutoriaService maestroTutoriaService, Usuario usuarioAutenticado) {
        this.maestroTutoriaService = maestroTutoriaService;
        this.usuarioAutenticado = usuarioAutenticado;
    }
    //Case 1: Maestro
    public void crearTutoria(Scanner scanner) {
        System.out.println("\n--- Creando Tutoría ---");
        scanner.nextLine();

        System.out.print("Ingrese el nombre de la tutoría: ");
        String nombreTutoria = scanner.nextLine().trim();

        if (nombreTutoria.isEmpty()) {
            System.out.println("Error: El nombre de la tutoría no puede estar vacío.");
            return;
        }

        System.out.print("Ingrese el estado de la tutoría (Terminada/En proceso): ");
        String estado = scanner.nextLine().trim().toLowerCase();

        if (!estado.equals("terminada") && !estado.equals("en proceso")) {
            System.out.println("Error: El estado debe ser 'Terminada' o 'En proceso'.");
            return;
        }

        int capacidad;
        try {
            System.out.print("Ingrese la capacidad máxima de estudiantes (número entero): ");
            capacidad = scanner.nextInt();

            if (capacidad <= 0) {
                System.out.println("Error: La capacidad debe ser mayor a 0.");
                return;
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: Debes ingresar un número entero válido.");
            scanner.nextLine();
            return;
        }
        scanner.nextLine();

        int materiasId;
        try {
            System.out.print("Ingrese el ID de la materia relacionada: ");
            materiasId = scanner.nextInt();

            if (materiasId <= 0) {
                System.out.println("Error: El ID de la materia debe ser un número positivo.");
                return;
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: Debes ingresar un número entero válido.");
            scanner.nextLine();
            return;
        }
        scanner.nextLine();

        int cantidadHorarios;
        try {
            System.out.print("¿Cuántos horarios desea agregar a esta tutoría?: ");
            cantidadHorarios = scanner.nextInt();

            if (cantidadHorarios <= 0) {
                System.out.println("Error: Debe agregar al menos un horario.");
                return;
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: Debes ingresar un número entero válido.");
            scanner.nextLine();
            return;
        }
        scanner.nextLine();

        List<Horario> horarios = new ArrayList<>();
        for (int i = 0; i < cantidadHorarios; i++) {
            System.out.println("Horario #" + (i + 1));

            System.out.print("Ingrese el día de la semana (por ejemplo: Lunes): ");
            String diaSemana = scanner.nextLine().trim();

            if (diaSemana.isEmpty()) {
                System.out.println("Error: El día de la semana no puede estar vacío.");
                return;
            }

            LocalTime horaInicio, horaFin;
            try {
                System.out.print("Ingrese la hora de inicio (formato HH:mm): ");
                horaInicio = LocalTime.parse(scanner.nextLine());

                System.out.print("Ingrese la hora de fin (formato HH:mm): ");
                horaFin = LocalTime.parse(scanner.nextLine());

                if (horaInicio.isAfter(horaFin)) {
                    System.out.println("Error: La hora de inicio no puede ser posterior a la hora de fin.");
                    return;
                }
            } catch (Exception e) {
                System.out.println("Error: Debes ingresar las horas en el formato correcto (HH:mm).");
                return;
            }

            Horario horario = new Horario();
            horario.setDiaSemana(diaSemana);
            horario.setHoraInicio(horaInicio);
            horario.setHoraFin(horaFin);
            horarios.add(horario);
        }
        int usuarioId = usuarioAutenticado.getIdUsuario();
        String validacion = maestroTutoriaService.validarDatos(nombreTutoria, estado, capacidad, horarios);
        if (!validacion.equals("OK")) {
            System.out.println(validacion);
            return;
        }
        String resultado = maestroTutoriaService.crearTutoria(nombreTutoria, estado, capacidad, horarios, usuarioId, materiasId);
        System.out.println(resultado);
    }

    //Case 5: Maestro
    public void eliminarTutoria(Scanner scanner) {
        System.out.println("\n--- Eliminando Tutoría ---");
        System.out.print("Ingrese el ID de la tutoría que desea eliminar: ");
        int tutoriaId;

        try {
            tutoriaId = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Error: Debe ingresar un número entero válido.");
            scanner.nextLine();
            return;
        }

        int usuarioId = usuarioAutenticado.getIdUsuario();
        if (tutoriaId <= 0) {
            System.out.println("Error: El ID de la tutoría debe ser un número positivo.");
            return;
        }
        String resultado = maestroTutoriaService.validarYEliminarTutoria(tutoriaId, usuarioId);
        System.out.println(resultado);
    }

}