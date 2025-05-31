package org.example.service;

import org.example.database.DbConnection;
import org.example.models.Horario;
import org.example.models.Tutoria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EstudianteService {

    public List<Tutoria> listarTutorias() {
        List<Tutoria> tutorias = new ArrayList<>();
        try (Connection conn = DbConnection.getConnection()) {
            String query = "SELECT t.id_tutorias, t.nombre_tutorias, t.fecha_creacion, " +
                    "t.estado, t.capacidad, h.id_horario, h.dia_semana, h.hora_inicio, h.hora_fin " +
                    "FROM tutorias t " +
                    "LEFT JOIN horarios_tutoria h " +
                    "ON t.id_tutorias = h.id_tutoria " +
                    "ORDER BY t.id_tutorias, h.dia_semana, h.hora_inicio";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            Tutoria tutoriaActual = null;
            int idTutoriaActual = -1;
            while (rs.next()) {
                int idTutoria = rs.getInt("id_tutorias");

                if (idTutoria != idTutoriaActual) {
                    tutoriaActual = new Tutoria();
                    tutoriaActual.setIdTutorias(idTutoria);
                    tutoriaActual.setNombreTutorias(rs.getString("nombre_tutorias"));
                    tutoriaActual.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
                    tutoriaActual.setEstado(rs.getString("estado"));
                    tutoriaActual.setCapacidad(rs.getInt("capacidad"));
                    tutoriaActual.setHorarios(new ArrayList<>());
                    tutorias.add(tutoriaActual);
                    idTutoriaActual = idTutoria;
                }
                if (tutoriaActual != null) {
                    int idHorario = rs.getInt("id_horario");
                    if (idHorario != 0) {
                        Horario horario = new Horario();
                        horario.setIdHorario(idHorario);
                        horario.setDiaSemana(rs.getString("dia_semana"));
                        horario.setHoraInicio(rs.getTime("hora_inicio").toLocalTime());
                        horario.setHoraFin(rs.getTime("hora_fin").toLocalTime());

                        tutoriaActual.getHorarios().add(horario);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error al listar tutorías con horarios: " + e.getMessage());
        }
        return tutorias;
    }

}