package org.example.service;

import org.example.database.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EstudianteTutoriaService {

    public String inscribirEnTutoria(int idEstudiante, int idTutoria) {
        try (Connection conn = DbConnection.getConnection()) {
            String consultaExisteTutoria = "SELECT COUNT(*) FROM tutorias WHERE id_tutorias = ?";
            PreparedStatement stmtTutoria = conn.prepareStatement(consultaExisteTutoria);
            stmtTutoria.setInt(1, idTutoria);
            ResultSet rsTutoria = stmtTutoria.executeQuery();
            if (rsTutoria.next() && rsTutoria.getInt(1) == 0) {
                return "La tutoría a la que intentas inscribirte no existe.";
            }
            String consultaExiste = "SELECT COUNT(*) FROM estudiante_tutoria WHERE id_estudiante = ? AND id_tutoria = ?";
            PreparedStatement stmtVerificar = conn.prepareStatement(consultaExiste);
            stmtVerificar.setInt(1, idEstudiante);
            stmtVerificar.setInt(2, idTutoria);
            ResultSet rs = stmtVerificar.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return "Ya estás inscrito en esta tutoría.";
            }
            String consultaColision = " SELECT ht.* FROM horarios_tutoria ht " +
                    "JOIN estudiante_tutoria et ON et.id_tutoria = ht.id_tutoria " +
                    "WHERE et.id_estudiante = ? AND ht.dia_semana = (SELECT dia_semana " +
                    "FROM horarios_tutoria WHERE id_tutoria = ? LIMIT 1) " +
                    "AND ( (ht.hora_inicio < (SELECT hora_fin FROM horarios_tutoria " +
                    "WHERE id_tutoria = ? LIMIT 1) AND ht.hora_fin > (SELECT hora_inicio " +
                    "FROM horarios_tutoria WHERE id_tutoria = ? LIMIT 1))) ";
            PreparedStatement stmtColision = conn.prepareStatement(consultaColision);
            stmtColision.setInt(1, idEstudiante);
            stmtColision.setInt(2, idTutoria);
            stmtColision.setInt(3, idTutoria);
            stmtColision.setInt(4, idTutoria);

            ResultSet rsColision = stmtColision.executeQuery();
            if (rsColision.next()) {
                return "La tutoría tiene un horario que entra en conflicto con una tutoría existente.";
            }
            String consultaInsertar = "INSERT INTO estudiante_tutoria (id_estudiante, id_tutoria) VALUES (?, ?)";
            PreparedStatement stmtInsertar = conn.prepareStatement(consultaInsertar);
            stmtInsertar.setInt(1, idEstudiante);
            stmtInsertar.setInt(2, idTutoria);

            int filasAfectadas = stmtInsertar.executeUpdate();
            if (filasAfectadas > 0) {
                return "Inscripción exitosa en la tutoría.";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Ocurrió un error al intentar inscribirse en la tutoría.";
        }

        return "No se pudo completar la inscripción.";
    }

    public String salirDeTutoria(int idEstudiante, int idTutoria) {
        try (Connection conn = DbConnection.getConnection()) {
            String consultaBorrar = "DELETE FROM estudiante_tutoria WHERE id_estudiante = ? AND id_tutoria = ?";
            PreparedStatement stmtBorrar = conn.prepareStatement(consultaBorrar);
            stmtBorrar.setInt(1, idEstudiante);
            stmtBorrar.setInt(2, idTutoria);

            int filasBorradas = stmtBorrar.executeUpdate();
            if (filasBorradas > 0) {
                return "Has salido de la tutoría exitosamente.";
            } else {
                return "No estás inscrito en esta tutoría.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Ocurrió un error al intentar salir de la tutoría.";
        }
    }

}