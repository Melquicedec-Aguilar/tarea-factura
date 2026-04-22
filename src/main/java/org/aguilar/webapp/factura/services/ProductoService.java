package org.aguilar.webapp.factura.services;

import java.util.List;
import java.util.Optional;
import org.aguilar.webapp.factura.models.Categoria;
import org.aguilar.webapp.factura.models.Producto;

/**
 * Servicio para operaciones relacionadas con {@link Producto} y {@link Categoria}.
 *
 * <p>Define el contrato que deben ofrecer las implementaciones: búsqueda, consulta por id,
 * listado, persistencia y eliminación de productos, así como operaciones de consulta sobre categorías.</p>
 *
 * <p>Las implementaciones concretas deciden detalles como coincidencia de búsquedas (exacta o parcial),
 * manejo de transacciones y validaciones adicionales.</p>
 */
public interface ProductoService {

    /**
     * Busca un producto por su nombre.
     *
     * <p>La semántica exacta de búsqueda (coincidencia exacta, parcial, case-insensitive, etc.)
     * queda a criterio de la implementación.</p>
     *
     * @param nombre nombre del producto a buscar (no debe ser {@code null})
     * @return {@link Optional} con el {@link Producto} encontrado, o {@code Optional.empty()} si no existe
     */
    Optional<Producto> buscarProducto(String nombre);

    /**
     * Obtiene un producto por su identificador.
     *
     * @param id identificador del producto
     * @return {@link Optional} con el {@link Producto} si existe, o {@code Optional.empty()} en caso contrario
     */
    Optional<Producto> porId(Long id);

    /**
     * Lista todos los productos disponibles.
     *
     * @return lista de {@link Producto}; se espera que no retorne {@code null} (vacía si no hay elementos)
     */
    List<Producto> listar();

    /**
     * Persiste un producto en el sistema.
     *
     * <p>La implementación debe decidir si realiza una inserción o una actualización
     * según el estado del identificador del objeto {@code producto}.</p>
     *
     * @param producto instancia de {@link Producto} a guardar o actualizar (no debe ser {@code null})
     */
    void guardar(Producto producto);

    /**
     * Elimina un producto identificado por su id.
     *
     * @param id identificador del producto a eliminar
     */
    void eliminar(Long id);

    /**
     * Obtiene una categoría por su identificador.
     *
     * @param id identificador de la categoría
     * @return {@link Optional} con la {@link Categoria} si existe, o {@code Optional.empty()} en caso contrario
     */
    Optional<Categoria> porIdCategoria(Long id);

    /**
     * Lista todas las categorías disponibles.
     *
     * @return lista de {@link Categoria}; se espera que no retorne {@code null}
     */
    List<Categoria> listarCategoria();
}