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
import java.util.List;
import org.aguilar.webapp.factura.models.Factura;
import org.aguilar.webapp.factura.models.LineaFactura;

@ApplicationScoped
@Named
public class FacturaRepositoryImpl implements Repository<Factura> {

    @Inject
    private Connection conn;

    @Override
    public List<Factura> listar() throws SQLException {
        return List.of();
    }

    @Override
    public Factura porId(Long id) throws SQLException {
        return null;
    }

    @Override
    public void guardar(Factura factura) throws SQLException {
        String sql = "INSERT INTO factura(usuario_id, fecha, descripcion) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            stmt.setLong(1, factura.getUsuario().getId());
            stmt.setDate(2, Date.valueOf(factura.getFechaFactura()));
            stmt.setString(3, factura.getDescripcion());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()){
                if (rs.next()){
                    factura.setId(rs.getLong(1));
                }
            }
        }

        for (LineaFactura item : factura.getLineasFactura()){
            try (PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO linea_factura(factura_id, producto_id, cantidad, precio) VALUES (?, ?, ?, ?)")){

                stmt.setLong(1, factura.getId());
                stmt.setLong(2, item.getProducto().getId());
                stmt.setInt(3, item.getCantidad());
                stmt.setDouble(4, item.getPrecio());
                stmt.executeUpdate();
            }
        }

    }

    @Override
    public void eliminar(Long id) throws SQLException {

    }
}
