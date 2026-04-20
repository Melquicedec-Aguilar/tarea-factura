package org.aguilar.webapp.factura.repositories;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.aguilar.webapp.factura.models.Categoria;
import org.aguilar.webapp.factura.models.Producto;

@ApplicationScoped
@Named
public class ProductoRepositoryImpl implements Repository<Producto> {

    @Inject
    private Connection conn;

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

    @Override
    public void eliminar(Long id) throws SQLException {
        String sql = "DELETE FROM productos WHERE id=?";

        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

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
