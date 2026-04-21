package org.aguilar.webapp.factura.services;

import java.util.List;
import java.util.Optional;
import org.aguilar.webapp.factura.models.Categoria;
import org.aguilar.webapp.factura.models.Producto;

public interface ProductoService {
    Optional<Producto> buscarProducto(String nombre);
    Optional<Producto> porId(Long id);
    List<Producto> listar();
    void guardar(Producto producto);
    void eliminar(Long id);

    Optional<Categoria> porIdCategoria(Long id);
    List<Categoria> listarCategoria();
}
