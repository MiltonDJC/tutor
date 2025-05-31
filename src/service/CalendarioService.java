package org.example.service;

import org.example.models.Horario;
import org.example.models.Tutoria;

import java.time.LocalTime;
import java.util.*;

public class CalendarioService {

    public void mostrarCalendarioEnMatriz(List<Tutoria> tutorias) {
        TreeSet<LocalTime> horariosSet = new TreeSet<>();
        Map<String, Map<LocalTime, String>> calendario = new HashMap<>();
        String[] diasSemana = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes"};
        for (String dia : diasSemana) {
            calendario.put(dia, new HashMap<>());
        }
        for (Tutoria tutoria : tutorias) {
            if (tutoria.getHorarios() != null) {
                for (Horario horario : tutoria.getHorarios()) {
                    String dia = horario.getDiaSemana();
                    if (calendario.containsKey(dia)) {
                        LocalTime inicio = horario.getHoraInicio();
                        horariosSet.add(inicio);
                        String detalleHorario = tutoria.getNombreTutorias()
                                + " (" + horario.getHoraInicio() + " - " + horario.getHoraFin() + ")";
                        calendario.get(dia).put(inicio, detalleHorario);
                    }
                }
            }
        }
        System.out.println(" --- Calendario de Tutorías:---");
        System.out.printf("%-10s", "Horario");
        for (String dia : diasSemana) {
            System.out.printf("%-30s", dia);
        }
        System.out.println();
        for (LocalTime hora : horariosSet) {
            System.out.printf("%-10s", hora);
            for (String dia : diasSemana) {
                String tutoriasEnHorario = calendario.get(dia).getOrDefault(hora, "");
                System.out.printf("%-30s", tutoriasEnHorario);
            }
            System.out.println();
        }
    }

}