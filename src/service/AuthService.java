package org.example.service;

import org.example.database.DbConnection;
import org.example.models.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class AuthService {
    private Usuario usuarioActual;

    public Usuario login(String email, String contraseña) {
        try (Connection conn = DbConnection.getConnection()) {
            String query = "SELECT * FROM usuario WHERE email_usuario = ? AND contraseña = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, contraseña);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setNombreUsuario(rs.getString("nombre_usuario"));
                usuario.setEmailUsuario(rs.getString("email_usuario"));
                usuario.setRolIdRol(rs.getInt("Rol_Id_Rol"));
                this.usuarioActual = usuario;
                return usuario;
            }
        } catch (Exception e) {
            System.err.println("Error al autenticar usuario: " + e.getMessage());
        }
        return null;
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public int getIdUsuarioActual() {
        return usuarioActual != null ? usuarioActual.getIdUsuario() : -1;
    }

}