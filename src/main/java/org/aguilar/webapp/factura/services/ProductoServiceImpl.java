package org.aguilar.webapp.factura.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.aguilar.webapp.factura.models.Categoria;
import org.aguilar.webapp.factura.models.Producto;
import org.aguilar.webapp.factura.repositories.Repository;

@ApplicationScoped
@Transactional
public class ProductoServiceImpl implements ProductoService{

    @Inject
    private Repository<Producto> repositoryProducto;

    @Inject
    private Repository<Categoria> repositoryCategoria;

    @Override
    public Optional<Producto> buscarProducto(String nombre) {
        return Optional.empty();
    }

    @Override
    public Optional<Producto> porId(Long id) {
        try {
            return Optional.ofNullable(repositoryProducto.porId(id));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Producto> listar() {
        try {
            return repositoryProducto.listar();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void guardar(Producto producto) {
        try {
            repositoryProducto.guardar(producto);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void eliminar(Long id) {
        try {
            repositoryProducto.eliminar(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Categoria> porIdCategoria(Long id) {
        try {
            return Optional.ofNullable(repositoryCategoria.porId(id));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Categoria> listarCategoria() {
        try {
            return repositoryCategoria.listar();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
