package org.example.menus;

import org.example.models.Usuario;
import org.example.service.AdministracionTutoriaServece;
import org.example.service.MaestroTutoriaService;

import java.util.Scanner;

public class MaestroMenu {

    private final GestorTutoriasMaestro gestorDeTutorias;
    private final GestionDeMaestro gestionDeMaestro;

    public MaestroMenu(Usuario usuarioAutenticado) {
        this.gestionDeMaestro = new GestionDeMaestro(new AdministracionTutoriaServece(), usuarioAutenticado);
        this.gestorDeTutorias = new GestorTutoriasMaestro(new MaestroTutoriaService(), usuarioAutenticado);
    }

    public void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;

        do {
            System.out.println("\n--- Menú Maestro ---");
            System.out.println("1. Crear tutorias y horarios de tutoria");
            System.out.println("2. Visualizar tutorias del maestro");
            System.out.println("3. Visualizar estudiantes de tutoria (implementación pendiente)");
            System.out.println("4. Cambiar horario de tutoria");
            System.out.println("5. Eliminar tutoria");
            System.out.println("6. Ver calendario de tutorias");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    gestorDeTutorias.crearTutoria(scanner);
                    break;
                case 2:
                    gestionDeMaestro.visualizarTutorias();
                    break;
                case 3:
                    gestionDeMaestro.listaDeEstudiantes();
                    // Implementación pendiente
                    break;
                case 4:
                    gestionDeMaestro.cambiarHorario(scanner);
                    break;
                case 5:
                    gestorDeTutorias.eliminarTutoria(scanner);
                    break;
                case 6:
                    gestionDeMaestro.verCalendario();
                    break;
                case 7:
                    System.out.println("Saliendo del menú maestro y cerrando la aplicación...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 8);
        System.out.println("-----------------------------------");
    }

}