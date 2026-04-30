package org.aguilar.webapp.factura.services;

import java.sql.SQLException;
import org.aguilar.webapp.factura.models.Carro;
import org.aguilar.webapp.factura.models.Factura;
import org.aguilar.webapp.factura.models.Usuario;

public interface FacturaService {
    Factura crearFactura(Carro carro, Usuario usuario);
    Integer generarNumeroFactura() throws SQLException;
}
