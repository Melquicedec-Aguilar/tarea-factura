package org.aguilar.webapp.factura.repositories;

import jakarta.inject.Inject;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import org.aguilar.webapp.factura.configs.Repositorio;
import org.aguilar.webapp.factura.models.Factura;
import org.aguilar.webapp.factura.models.LineaFactura;

@Repositorio
public class FacturaRepositoryImpl implements FacturaRepository<Factura> {

    @Inject
    private Connection conn;

    @Override
    public void guardar(Factura factura) throws SQLException {
        String sql = "INSERT INTO factura(usuario_id, fecha, descripcion, numero_factura) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            stmt.setLong(1, factura.getUsuario().getId());
            stmt.setDate(2, Date.valueOf(factura.getFechaFactura()));
            stmt.setString(3, factura.getDescripcion());
            stmt.setInt(4, factura.getNumeroFactura());
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
    public Optional<Factura> existeNumeroFactura(Integer numeroFactura) throws SQLException {
        String sql = "SELECT * FROM factura WHERE numero_factura = ?";

        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, numeroFactura);
            try(ResultSet rs = stmt.executeQuery()){
                if (rs.next()){
                    Factura factura = new Factura();
                    factura.setId(rs.getLong("id"));
                    factura.setNumeroFactura(rs.getInt("numero_factura"));
                    factura.setDescripcion(rs.getString("descripcion"));
                    factura.setFechaFactura(rs.getDate("fecha").toLocalDate());
                    return Optional.of(factura);

                }
            }
        }
        return Optional.empty();
    }

}
