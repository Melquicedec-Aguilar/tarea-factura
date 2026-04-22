package org.aguilar.webapp.factura.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import org.aguilar.webapp.factura.configs.Service;
import org.aguilar.webapp.factura.models.Carro;
import org.aguilar.webapp.factura.models.Factura;
import org.aguilar.webapp.factura.models.LineaFactura;
import org.aguilar.webapp.factura.models.Usuario;
import org.aguilar.webapp.factura.repositories.Repository;

@Service
public class FacturaServiceImpl implements FacturaService{

    @Inject
    private Repository<Factura> repositoryFactura;

    @Override
    public Factura crearFactura(Carro carro, Usuario usuario) {
        Factura factura = new Factura();
        factura.setUsuario(usuario);
        factura.setFechaFactura(LocalDate.now());

        List<LineaFactura> lineas = carro.getItems().stream()
            .map(i -> new LineaFactura(
                i.getProducto(),
                i.getCantidad(),
                (double) i.getProducto().getPrecio()
            )).toList();

        factura.setLineasFactura(lineas);
        try {
            repositoryFactura.guardar(factura);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return factura;
    }
}
