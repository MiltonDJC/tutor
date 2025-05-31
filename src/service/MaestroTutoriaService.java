package org.example.service;

import org.example.database.DbConnection;
import org.example.models.Horario;

import java.sql.*;
import java.util.List;

public class MaestroTutoriaService {

    public String crearTutoria(String nombreTutoria, String estado, int capacidad, List<Horario> horarios, int usuarioId, int materiasId) {
        String resultado = validarDatos(nombreTutoria, estado, capacidad, horarios);
        if (!resultado.equals("OK")) {
            return resultado;
        }

        try (Connection connection = DbConnection.getConnection()) {

            connection.setAutoCommit(false);

            String tutoriaQuery = "INSERT INTO tutorias (nombre_tutorias, estado, capacidad, Usuario_id_usuario, Materias_id_materias) " +
                    "VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement tutoriaStmt = connection.prepareStatement(tutoriaQuery, Statement.RETURN_GENERATED_KEYS)) {
                tutoriaStmt.setString(1, nombreTutoria);
                tutoriaStmt.setString(2, estado);
                tutoriaStmt.setInt(3, capacidad);
                tutoriaStmt.setInt(4, usuarioId);
                tutoriaStmt.setInt(5, materiasId);

                int affectedRows = tutoriaStmt.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Error: No se pudo insertar la tutoría.");
                }

                try (ResultSet generatedKeys = tutoriaStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int tutoriaId = generatedKeys.getInt(1);

                        String horarioQuery = "INSERT INTO horarios_tutoria (id_tutoria, dia_semana, hora_inicio, hora_fin) " +
                                "VALUES (?, ?, ?, ?)";
                        try (PreparedStatement horarioStmt = connection.prepareStatement(horarioQuery)) {
                            for (Horario horario : horarios) {
                                horarioStmt.setInt(1, tutoriaId);
                                horarioStmt.setString(2, horario.getDiaSemana());
                                horarioStmt.setTime(3, Time.valueOf(horario.getHoraInicio()));
                                horarioStmt.setTime(4, Time.valueOf(horario.getHoraFin()));
                                horarioStmt.addBatch();
                            }
                            horarioStmt.executeBatch();
                        }
                    } else {
                        throw new SQLException("Error: No se pudo obtener el ID de la tutoría recién creada.");
                    }
                }
            }

            connection.commit();
            return "Tutoría creada exitosamente: " + nombreTutoria + " por el usuario ID " + usuarioId + " relacionada a la materia ID " + materiasId;
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error al guardar la tutoría: " + e.getMessage();
        }
    }

    public String validarDatos(String nombreTutoria, String estado, int capacidad, List<Horario> horarios) {
        if (nombreTutoria == null || nombreTutoria.isEmpty()) {
            return "Error: El nombre de la tutoría no puede estar vacío.";
        }
        if (!estado.equalsIgnoreCase("terminada") && !estado.equalsIgnoreCase("en proceso")) {
            return "Error: El estado debe ser 'Terminada' o 'En proceso'.";
        }
        if (capacidad <= 0) {
            return "Error: La capacidad debe ser mayor a 0.";
        }
        if (horarios == null || horarios.isEmpty()) {
            return "Error: Debes proporcionar al menos un horario válido.";
        }
        for (Horario horario : horarios) {
            if (horario.getDiaSemana() == null || horario.getDiaSemana().isEmpty()) {
                return "Error: El día de la semana no puede estar vacío.";
            }
            if (horario.getHoraInicio() == null || horario.getHoraFin() == null) {
                return "Error: Las horas de inicio y fin deben ser válidas.";
            }
            if (horario.getHoraInicio().isAfter(horario.getHoraFin())) {
                return "Error: La hora de inicio no puede ser posterior a la hora de fin.";
            }
        }
        return "OK";
    }

    public String validarYEliminarTutoria(int tutoriaId, int usuarioId) {
        try (Connection connection = DbConnection.getConnection()) {
            connection.setAutoCommit(false);

            String selectQuery = "SELECT Usuario_id_usuario FROM tutorias WHERE id_tutorias = ?";
            try (PreparedStatement selectStmt = connection.prepareStatement(selectQuery)) {
                selectStmt.setInt(1, tutoriaId);
                try (ResultSet rs = selectStmt.executeQuery()) {
                    if (rs.next()) {
                        int propietarioId = rs.getInt("Usuario_id_usuario");
                        if (propietarioId != usuarioId) {
                            return "Error: No tiene permiso para eliminar esta tutoría.";
                        }
                    } else {
                        return "Error: No se encontró la tutoría con ID " + tutoriaId;
                    }
                }
            }

            String deleteHorariosQuery = "DELETE FROM horarios_tutoria WHERE id_tutoria = ?";
            try (PreparedStatement deleteHorariosStmt = connection.prepareStatement(deleteHorariosQuery)) {
                deleteHorariosStmt.setInt(1, tutoriaId);
                deleteHorariosStmt.executeUpdate();
            }

            String deleteQuery = "DELETE FROM tutorias WHERE id_tutorias = ?";
            try (PreparedStatement deleteStmt = connection.prepareStatement(deleteQuery)) {
                deleteStmt.setInt(1, tutoriaId);
                int rowsAffected = deleteStmt.executeUpdate();
                if (rowsAffected > 0) {
                    connection.commit();
                    return "Tutoría eliminada exitosamente.";
                } else {
                    connection.rollback();
                    return "Error: No se pudo eliminar la tutoría.";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error al eliminar la tutoría: " + e.getMessage();
        }
    }

}