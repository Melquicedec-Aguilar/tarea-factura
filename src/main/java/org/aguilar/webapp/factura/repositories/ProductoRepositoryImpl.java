package org.aguilar.webapp.factura.repositories;

import jakarta.inject.Inject;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.aguilar.webapp.factura.configs.Repositorio;
import org.aguilar.webapp.factura.models.Categoria;
import org.aguilar.webapp.factura.models.Producto;

/**
 * Implementación de {@link Repository} para la entidad {@link Producto}.
 *
 * <p>Proporciona operaciones CRUD usando JDBC sobre la tabla `productos` y realiza
 * el mapeo manual de resultados SQL a instancias de {@link Producto} y {@link Categoria}.</p>
 *
 * <p>Se espera que la conexión JDBC sea inyectada (por ejemplo, a través de CDI) en
 * el campo {@link #conn}.</p>
 */
@Repositorio
public class ProductoRepositoryImpl implements Repository<Producto> {

    /**
     * Conexión JDBC inyectada que se utiliza para todas las operaciones de persistencia.
     * <p>No debe cerrarse aquí; el lifecycle lo gestiona el contenedor/inyección.</p>
     */
    @Inject
    private Connection conn;

    /**
     * Recupera todos los productos disponibles en la base de datos.
     *
     * <p>Realiza un {@code INNER JOIN} con la tabla {@code categorias} para incluir el nombre
     * de la categoría asociada a cada producto. El resultado se ordena por {@code p.id ASC}.</p>
     *
     * @return lista de productos (vacía si no hay resultados)
     * @throws SQLException si ocurre un error en la consulta
     */
    @Override
    public List<Producto> listar() throws SQLException {
        List<Producto> productos = new ArrayList<>();

        try(Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT p.*, c.nombre as categoria FROM productos as p" +
            " inner join categorias as c ON (p.categoria_id = c.id) ORDER BY p.id ASC")){
            while(rs.next()){
                Producto producto = getProducto(rs);
                productos.add(producto);
            }
        }
        return productos;
    }

    /**
     * Busca y devuelve un producto por su identificador.
     *
     * <p>Si no existe un producto con el id proporcionado retorna {@code null}.</p>
     *
     * @param id identificador del producto a buscar
     * @return instancia de {@link Producto} o {@code null} si no se encuentra
     * @throws SQLException si ocurre un error en la consulta
     */
    @Override
    public Producto porId(Long id) throws SQLException {
        Producto producto = null;
        try (PreparedStatement stmt = conn.prepareStatement("SELECT p.*, c.nombre as categoria FROM productos as p " +
            " inner join categorias as c ON (p.categoria_id = c.id) WHERE p.id=?")){

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()){
                if (rs.next()){
                    producto = getProducto(rs);
                }

            }

        }
        return producto;
    }

    /**
     * Inserta o actualiza un producto en la base de datos.
     *
     * <p>Si {@code producto.getId()} es distinto de {@code null} y mayor que 0 se realiza
     * un {@code UPDATE}, en caso contrario un {@code INSERT} incluyendo {@code fecha_registro}.</p>
     *
     * @param producto producto a guardar o actualizar
     * @throws SQLException si ocurre un error en la operación de escritura
     */
    @Override
    public void guardar(Producto producto) throws SQLException {
        String sql;

        if (producto.getId() != null && producto.getId() > 0){
            sql = "UPDATE productos SET nombre=?, precio=?, sku=?, categoria_id=? WHERE id=?";
        }else {
            sql = "INSERT INTO productos (nombre, precio, sku, categoria_id, fecha_registro) VALUES (?, ?, ?, ?, ?)";
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, producto.getNombre());
            stmt.setInt(2, producto.getPrecio());
            stmt.setString(3, producto.getSku());
            stmt.setLong(4, producto.getCategoria().getId());

            if (producto.getId() != null && producto.getId() > 0){
                stmt.setLong(5, producto.getId());
            }else {
                stmt.setDate(5, Date.valueOf(producto.getFechaRegistro()));
            }

            stmt.executeUpdate();
        }
    }

    /**
     * Elimina un producto identificado por {@code id}.
     *
     * @param id identificador del producto a eliminar
     * @throws SQLException si ocurre un error en la operación de borrado
     */
    @Override
    public void eliminar(Long id) throws SQLException {
        String sql = "DELETE FROM productos WHERE id=?";

        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    /**
     * Mapea la fila actual del {@link ResultSet} a una instancia de {@link Producto}.
     *
     * <p>Se asume que el {@code ResultSet} contiene las columnas:
     * {@code id, nombre, sku, precio, fecha_registro, categoria_id} y una columna aliased {@code categoria}
     * con el nombre de la categoría.</p>
     *
     * @param rs conjunto de resultados posicionado en la fila a mapear
     * @return instancia de {@link Producto} poblada con los datos del {@code ResultSet}
     * @throws SQLException si ocurre un error al leer los campos del resultado
     */
    private Producto getProducto(ResultSet rs) throws SQLException {
        Producto producto = new Producto();
        Categoria categoria = new Categoria();

        producto.setId(rs.getLong("id"));
        producto.setNombre(rs.getString("nombre"));
        producto.setSku(rs.getString("sku"));
        producto.setPrecio(rs.getInt("precio"));
        producto.setFechaRegistro(rs.getDate("fecha_registro").toLocalDate());
        categoria.setId(rs.getLong("categoria_id"));
        categoria.setNombre(rs.getString("categoria"));
        producto.setCategoria(categoria);

        return producto;
    }
}