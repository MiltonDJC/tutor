package org.example.models;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Tutoria {
    private int idTutorias;
    private String nombreTutorias;
    private Timestamp fechaCreacion;
    private Timestamp fechaEliminacion;
    private int materiasIdMaterias;
    private int usuarioIdUsuario;
    private String estado;
    private int capacidad;
    private List<Horario> horarios;

    public int getIdTutorias() {
        return idTutorias;
    }

    public void setIdTutorias(int idTutorias) {
        this.idTutorias = idTutorias;
    }

    public String getNombreTutorias() {
        return nombreTutorias;
    }

    public void setNombreTutorias(String nombreTutorias) {
        this.nombreTutorias = nombreTutorias;
    }

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Timestamp getFechaEliminacion() {
        return fechaEliminacion;
    }

    public void setFechaEliminacion(Timestamp fechaEliminacion) {
        this.fechaEliminacion = fechaEliminacion;
    }

    public int getMateriasIdMaterias() {
        return materiasIdMaterias;
    }

    public void setMateriasIdMaterias(int materiasIdMaterias) {
        this.materiasIdMaterias = materiasIdMaterias;
    }

    public int getUsuarioIdUsuario() {
        return usuarioIdUsuario;
    }

    public void setUsuarioIdUsuario(int usuarioIdUsuario) {
        this.usuarioIdUsuario = usuarioIdUsuario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }
    public List<Horario> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<Horario> horarios) {
        this.horarios = horarios;
    }
    @Override
    public String toString() {
        return "Tutoria{" +
                "idTutorias=" + idTutorias +
                ", nombreTutorias='" + nombreTutorias + '\'' +
                ", estado='" + estado + '\'' +
                ", capacidad=" + capacidad +
                ", horarios=" + horarios +
                '}';
    }

}