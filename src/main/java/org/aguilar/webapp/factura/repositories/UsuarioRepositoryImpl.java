package org.aguilar.webapp.factura.repositories;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.aguilar.webapp.factura.models.Usuario;

@ApplicationScoped
@Named
public class UsuarioRepositoryImpl implements UsuarioRepository{

    @Inject
    private Connection conn;

    @Override
    public Usuario porUsername(String username) throws SQLException {
        Usuario usuario = null;
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Usuarios WHERE username =?")){
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()){
                if (rs.next()){
                    usuario = new Usuario();
                    usuario.setId(rs.getLong("id"));
                    usuario.setUsername(rs.getString("username"));
                    usuario.setPassword(rs.getString("password"));
                    usuario.setEmail(rs.getString("email"));
                }
            }
        }
        return usuario;
    }

    @Override
    public List<Usuario> listar() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();

        try (Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Usuarios")){
            while (rs.next()){
                Usuario usuario = getUsuario(rs);
                usuarios.add(usuario);
            }
        }
        return usuarios;
    }

    @Override
    public Usuario porId(Long id) throws SQLException {
        Usuario usuario = null;
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Usuarios WHERE id=?")){
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()){
                if (rs.next()){
                    usuario = getUsuario(rs);
                }
            }
        }
        return usuario;
    }

    @Override
    public void guardar(Usuario usuario) throws SQLException {
        String sql;

        if (usuario.getId() != null && usuario.getId() > 0){
            sql = "UPDATE Usuarios SET username=?, password=?, email=? WHERE id=?";
        }else {
            sql = "INSERT INTO Usuarios (username, password, email) VALUES (?, ?, ?)";
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, usuario.getUsername());
            stmt.setString(2, usuario.getPassword());
            stmt.setString(3, usuario.getEmail());

            if (usuario.getId() != null && usuario.getId() > 0){
                stmt.setLong(4, usuario.getId());
            }
            stmt.executeUpdate();
        }
    }

    @Override
    public void eliminar(Long id) throws SQLException {
        String sql = "DELETE FROM Usuarios WHERE id=?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    private Usuario getUsuario(ResultSet rs){
        Usuario usuario = new Usuario();
        try {
            usuario.setId(rs.getLong("id"));
            usuario.setUsername(rs.getString("username"));
            usuario.setPassword(rs.getString("password"));
            usuario.setEmail(rs.getString("email"));
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return usuario;
    }
}
