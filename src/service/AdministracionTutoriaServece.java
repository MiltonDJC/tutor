package org.example.service;

import org.example.database.DbConnection;
import org.example.models.Horario;
import org.example.models.Tutoria;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdministracionTutoriaServece {

    public List<Tutoria> obtenerTutoriasDelMaestro(int maestroId) {
        List<Tutoria> tutorias = new ArrayList<>();
        String query = "SELECT id_tutorias, nombre_tutorias, estado, capacidad FROM tutorias WHERE Usuario_id_usuario = ?";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, maestroId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Tutoria tutoria = new Tutoria();
                    tutoria.setIdTutorias(rs.getInt("id_tutorias"));
                    tutoria.setNombreTutorias(rs.getString("nombre_tutorias"));
                    tutoria.setEstado(rs.getString("estado"));
                    tutoria.setCapacidad(rs.getInt("capacidad"));

                    tutoria.setHorarios(obtenerHorariosTutoria(tutoria.getIdTutorias()));
                    tutorias.add(tutoria);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tutorias;
    }

    public List<Horario> obtenerHorariosTutoria(int tutoriaId) {
        List<Horario> horarios = new ArrayList<>();
        String query = "SELECT dia_semana, hora_inicio, hora_fin FROM horarios_tutoria WHERE id_tutoria = ?";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, tutoriaId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Horario horario = new Horario();
                    horario.setDiaSemana(rs.getString("dia_semana"));
                    horario.setHoraInicio(rs.getTime("hora_inicio").toLocalTime());
                    horario.setHoraFin(rs.getTime("hora_fin").toLocalTime());
                    horarios.add(horario);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return horarios;
    }

    public String actualizarHorarioTutoria(int tutoriaId, int maestroId, Scanner scanner) {
        try (Connection connection = DbConnection.getConnection()) {
            connection.setAutoCommit(false);

            String verificarQuery = "SELECT Usuario_id_usuario FROM tutorias WHERE id_tutorias = ?";
            try (PreparedStatement verificarStmt = connection.prepareStatement(verificarQuery)) {
                verificarStmt.setInt(1, tutoriaId);
                try (ResultSet rs = verificarStmt.executeQuery()) {
                    if (rs.next()) {
                        int propietario = rs.getInt("Usuario_id_usuario");
                        if (propietario != maestroId) {
                            return "Error: No tiene permiso para actualizar los horarios de esta tutoría.";
                        }
                    } else {
                        return "Error: No se encontró la tutoría.";
                    }
                }
            }

            List<Horario> horarios = obtenerHorariosTutoria(tutoriaId);
            if (horarios.isEmpty()) {
                return "No hay horarios disponibles para esta tutoría.";
            }

            System.out.println("\n--- Lista de horarios para la tutoría ---");
            for (int i = 0; i < horarios.size(); i++) {
                Horario horario = horarios.get(i);
                System.out.println((i + 1) + ". Día: " + horario.getDiaSemana() + ", Inicio: " + horario.getHoraInicio() + ", Fin: " + horario.getHoraFin());
            }

            System.out.print("Seleccione el número del horario que desea actualizar: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            if (opcion < 1 || opcion > horarios.size()) {
                return "Opción inválida.";
            }

            Horario horarioSeleccionado = horarios.get(opcion - 1);

            System.out.print("Ingrese la nueva hora de inicio (formato HH:mm): ");
            LocalTime nuevaHoraInicio = LocalTime.parse(scanner.nextLine());

            System.out.print("Ingrese la nueva hora de fin (formato HH:mm): ");
            LocalTime nuevaHoraFin = LocalTime.parse(scanner.nextLine());

            if (nuevaHoraInicio.isAfter(nuevaHoraFin)) {
                return "Error: La hora de inicio no puede ser posterior a la hora de fin.";
            }

            String actualizarQuery = "UPDATE horarios_tutoria SET hora_inicio = ?, hora_fin = ? WHERE id_tutoria = ? AND dia_semana = ?";
            try (PreparedStatement actualizarStmt = connection.prepareStatement(actualizarQuery)) {
                actualizarStmt.setTime(1, Time.valueOf(nuevaHoraInicio));
                actualizarStmt.setTime(2, Time.valueOf(nuevaHoraFin));
                actualizarStmt.setInt(3, tutoriaId);
                actualizarStmt.setString(4, horarioSeleccionado.getDiaSemana());

                int filasAfectadas = actualizarStmt.executeUpdate();
                if (filasAfectadas > 0) {
                    connection.commit();
                    return "Horario actualizado correctamente.";
                } else {
                    connection.rollback();
                    return "Error: No se pudo actualizar el horario.";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error al actualizar el horario: " + e.getMessage();
        }
    }

}