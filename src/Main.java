package org.example;

import org.example.menus.MaestroMenu;
import org.example.menus.EstudianteMenu;
import org.example.models.Usuario;
import org.example.service.AuthService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AuthService authService = new AuthService();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingreese su email: ");
        String email = scanner.nextLine();

        System.out.print("Ingrese su contraseña: ");
        String contraseña = scanner.nextLine();
        Usuario usuario = authService.login(email, contraseña);

        if (usuario != null) {
            System.out.println("¡Bienvenido, " + usuario.getNombreUsuario() + "!");
            if (usuario.getRolIdRol() == 1) {
                MaestroMenu maestroMenu = new MaestroMenu(usuario);
                maestroMenu.mostrarMenu();
            } else if (usuario.getRolIdRol() == 2) {
                EstudianteMenu estudianteMenu = new EstudianteMenu(authService);
                estudianteMenu.mostrarMenu();
            } else {
                System.out.println("Error en el Rol.");
            }
        } else {
            System.out.println("Correo o contraseña incorrectos.");
        }
    }
}