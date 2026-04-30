package org.aguilar.webapp.factura.services;

import jakarta.inject.Inject;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.aguilar.webapp.factura.configs.Service;
import org.aguilar.webapp.factura.models.Carro;
import org.aguilar.webapp.factura.models.Factura;
import org.aguilar.webapp.factura.models.LineaFactura;
import org.aguilar.webapp.factura.models.Usuario;
import org.aguilar.webapp.factura.repositories.FacturaRepository;

@Service
public class FacturaServiceImpl implements FacturaService{

    @Inject
    private FacturaRepository<Factura> repositoryFactura;

    @Inject
    private Factura factura;

    @Override
    public Factura crearFactura(Carro carro, Usuario usuario) {

        factura.setUsuario(usuario);
        factura.setDescripcion("Factura oficina del cliente " + usuario.getUsername());
        factura.setFechaFactura(LocalDate.now());

        try {
            factura.setNumeroFactura(generarNumeroFactura());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

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

    @Override
    public Integer generarNumeroFactura() throws SQLException {
        Integer numero;

        do{
            numero = ThreadLocalRandom.current().nextInt(100000, 999999);
        }while (repositoryFactura.existeNumeroFactura(numero).isPresent());

        return numero;
    }
}
