package org.aguilar.webapp.factura.repositories;

import java.sql.SQLException;
import java.util.Optional;
import org.aguilar.webapp.factura.models.Factura;

public interface FacturaRepository<T> {
    void guardar(T t) throws SQLException;
    Optional<Factura> existeNumeroFactura(Integer numeroFactura) throws SQLException;
}
