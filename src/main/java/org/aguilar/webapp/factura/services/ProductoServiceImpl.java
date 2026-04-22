package org.aguilar.webapp.factura.services;

import jakarta.inject.Inject;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.aguilar.webapp.factura.configs.ProductoServicePrincipal;
import org.aguilar.webapp.factura.configs.Service;
import org.aguilar.webapp.factura.models.Categoria;
import org.aguilar.webapp.factura.models.Producto;
import org.aguilar.webapp.factura.repositories.Repository;

/**
 * Implementación principal del servicio {@link ProductoService}.
 *
 * <p>Proporciona operaciones sobre {@link Producto} y {@link Categoria} delegando
 * en los repositorios inyectados. Las excepciones {@link SQLException} lanzadas
 * por los repositorios se envuelven en {@link RuntimeException} para simplificar
 * el uso en capas superiores (controladores/Vistas).</p>
 *
 * <p>La anotación {@link ProductoServicePrincipal} marca esta implementación como
 * la principal en el contexto de inyección.</p>
 */
@Service
public class ProductoServiceImpl implements ProductoService {

    /**
     * Repositorio para operaciones CRUD de {@link Producto}.
     *
     * <p>Se inyecta mediante CDI. Las llamadas a este repositorio pueden lanzar
     * {@link SQLException} que serán capturadas y envueltas en {@link RuntimeException}.</p>
     */
    @Inject
    private Repository<Producto> repositoryProducto;

    /**
     * Repositorio para operaciones sobre {@link Categoria}.
     *
     * <p>Se inyecta mediante CDI. Utilizado para búsquedas y listados de categorías.</p>
     */
    @Inject
    private Repository<Categoria> repositoryCategoria;

    /**
     * Busca un producto por su nombre.
     *
     * <p>Actualmente no implementado y devuelve {@link Optional#empty()}. La implementación
     * futura debería delegar en {@code repositoryProducto} y manejar {@link SQLException} de forma similar
     * a otros métodos.</p>
     *
     * @param nombre nombre del producto a buscar (no nulo)
     * @return opcional con el producto encontrado o {@link Optional#empty()} si no existe/pendiente implementar
     */
    @Override
    public Optional<Producto> buscarProducto(String nombre) {
        return Optional.empty();
    }

    /**
     * Obtiene un producto por su identificador.
     *
     * <p>Delegará en {@code repositoryProducto.porId(id)} y envolverá cualquier
     * {@link SQLException} en una {@link RuntimeException}.</p>
     *
     * @param id identificador del producto
     * @return {@link Optional} con el producto si existe, o {@link Optional#empty()} si no
     * @throws RuntimeException si ocurre un error al acceder al repositorio
     */
    @Override
    public Optional<Producto> porId(Long id) {
        try {
            return Optional.ofNullable(repositoryProducto.porId(id));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Lista todos los productos disponibles.
     *
     * <p>Devuelve la lista proporcionada por {@code repositoryProducto.listar()}.
     * Las {@link SQLException} se envuelven en {@link RuntimeException}.</p>
     *
     * @return lista de productos (puede estar vacía, no nula)
     * @throws RuntimeException si ocurre un error en la operación del repositorio
     */
    @Override
    public List<Producto> listar() {
        try {
            return repositoryProducto.listar();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Persiste un producto (inserta o actualiza según la implementación del repositorio).
     *
     * <p>Las {@link SQLException} se envuelven en {@link RuntimeException} para la capa superior.</p>
     *
     * @param producto instancia a guardar o actualizar
     * @throws RuntimeException si ocurre un error en la operación de persistencia
     */
    @Override
    public void guardar(Producto producto) {
        try {
            repositoryProducto.guardar(producto);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Elimina un producto por su identificador.
     *
     * <p>Propaga cualquier error de acceso a datos como {@link RuntimeException}.</p>
     *
     * @param id identificador del producto a eliminar
     * @throws RuntimeException si ocurre un error en el repositorio
     */
    @Override
    public void eliminar(Long id) {
        try {
            repositoryProducto.eliminar(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Obtiene una categoría por su identificador.
     *
     * <p>Delegación a {@code repositoryCategoria.porId(id)} con manejo de excepciones.</p>
     *
     * @param id identificador de la categoría
     * @return {@link Optional} con la categoría si existe, o {@link Optional#empty()} si no
     * @throws RuntimeException si ocurre un error en la consulta
     */
    @Override
    public Optional<Categoria> porIdCategoria(Long id) {
        try {
            return Optional.ofNullable(repositoryCategoria.porId(id));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Lista todas las categorías disponibles.
     *
     * <p>Las {@link SQLException} se envuelven en {@link RuntimeException}.</p>
     *
     * @return lista de categorías (no nula)
     * @throws RuntimeException si ocurre un error al acceder al repositorio
     */
    @Override
    public List<Categoria> listarCategoria() {
        try {
            return repositoryCategoria.listar();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}