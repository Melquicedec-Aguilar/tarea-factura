package org.aguilar.webapp.factura.repositories;

import jakarta.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.aguilar.webapp.factura.configs.Repositorio;
import org.aguilar.webapp.factura.models.Usuario;

/**
 * Implementación JDBC del repositorio de {@link Usuario}.
 *
 * <p>Responsabilidad:
 * - Proveer operaciones CRUD básicas y consultas específicas sobre la tabla "Usuarios"
 *   usando una conexión JDBC inyectada.</p>
 *
 * <p>Notas:
 * - Esta clase asume que la tabla "Usuarios" tiene columnas: id, username, password, email.
 * - La conexión {@code conn} se inyecta por CDI; la gestión de la transacción y el ciclo de vida
 *   de la conexión depende de la configuración del contenedor y de la implementación que
 *   proporcione la conexión.</p>
 */
@Repositorio
public class UsuarioRepositoryImpl implements UsuarioRepository{

    /**
     * Conexión JDBC inyectada por CDI.
     *
     * <p>Se usa para crear sentencias y ejecutar consultas/actualizaciones.</p>
     */
    @Inject
    private Connection conn;

    /**
     * Busca un {@link Usuario} por su nombre de usuario (username).
     *
     * <p>Si existe un usuario con el username proporcionado, se construye y devuelve la entidad.
     * Si no existe, devuelve {@code null}.</p>
     *
     * @param username el nombre de usuario a buscar (se espera no nulo)
     * @return la instancia {@link Usuario} encontrada o {@code null} si no existe
     * @throws SQLException si ocurre un error durante la consulta JDBC
     */
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

    /**
     * Lista todos los usuarios existentes en la tabla "Usuarios".
     *
     * @return una lista (posiblemente vacía) con todos los {@link Usuario} encontrados
     * @throws SQLException si ocurre un error durante la consulta JDBC
     */
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

    /**
     * Busca un {@link Usuario} por su identificador (id).
     *
     * @param id identificador del usuario a buscar
     * @return la instancia {@link Usuario} encontrada, o {@code null} si no existe
     * @throws SQLException si ocurre un error durante la consulta JDBC
     */
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

    /**
     * Inserta o actualiza un {@link Usuario} en la base de datos.
     *
     * <p>Comportamiento:
     * - Si {@code usuario.getId() != null && usuario.getId() > 0} se ejecuta un UPDATE.
     * - En otro caso se ejecuta un INSERT.</p>
     *
     * <p>Parámetros del PreparedStatement:
     * - 1: username
     * - 2: password
     * - 3: email
     * - 4: id (solo para UPDATE)</p>
     *
     * @param usuario entidad con los datos a persistir (se espera que los campos username, password y email estén presentes)
     * @throws SQLException si ocurre un error durante la ejecución JDBC
     */
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

    /**
     * Elimina un usuario por su identificador.
     *
     * @param id id del usuario a eliminar
     * @throws SQLException si ocurre un error durante la ejecución JDBC
     */
    @Override
    public void eliminar(Long id) throws SQLException {
        String sql = "DELETE FROM Usuarios WHERE id=?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    /**
     * Construye un objeto {@link Usuario} a partir de la fila actual del {@link ResultSet}.
     *
     * <p>Este método centraliza la lectura de columnas y el mapeo a la entidad. Internamente
     * captura {@link SQLException} y lo convierte en un {@link RuntimeException} porque es un
     * helper privado usado dentro de contextos ya controlados por los métodos que lanzan {@link SQLException}.</p>
     *
     * @param rs resultado posicionado en la fila a mapear
     * @return una nueva instancia de {@link Usuario} con los valores leídos del ResultSet
     * @throws RuntimeException si ocurre un {@link SQLException} durante el mapeo
     */
    private Usuario getUsuario(ResultSet rs){
        Usuario usuario = new Usuario();
        try {
            usuario.setId(rs.getLong("id"));
            usuario.setUsername(rs.getString("username"));
            usuario.setPassword(rs.getString("password"));
            usuario.setEmail(rs.getString("email"));
        } catch (SQLException ex) {
            // Convertimos la excepción comprobada en una excepción de tiempo de ejecución
            // para simplificar la re-utilización interna; los métodos públicos siguen declarando SQLException.
            throw new RuntimeException(ex);
        }
        return usuario;
    }
}