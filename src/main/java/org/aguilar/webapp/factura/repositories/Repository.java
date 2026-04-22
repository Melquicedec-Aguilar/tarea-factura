package org.aguilar.webapp.factura.repositories;

import java.sql.SQLException;
import java.util.List;

/**
 * Contrato genérico para repositorios de persistencia.
 *
 * <p>Esta interfaz define operaciones CRUD mínimas que deben implementar los
 * repositorios concretos (por ejemplo, {@code ProductoRepository}, {@code UsuarioRepository}, etc.).
 * Los métodos lanzan {@link SQLException} porque las implementaciones actuales usan JDBC
 * y pueden producir errores de acceso a la base de datos.</p>
 *
 * @param <T> tipo de entidad manejada por el repositorio
 */
public interface Repository<T> {

    /**
     * Recupera todos los registros de la entidad.
     *
     * @return lista de entidades del tipo {@code T}; no debe retornar {@code null} (vacía si no hay datos)
     * @throws SQLException si ocurre un error al acceder a la base de datos
     */
    List<T> listar() throws SQLException;

    /**
     * Obtiene una entidad por su identificador.
     *
     * @param id identificador único de la entidad; puede ser {@code null} según la convención de la aplicación
     * @return la entidad encontrada o {@code null} si no existe
     * @throws SQLException si ocurre un error en la operación de consulta
     */
    T porId(Long id) throws SQLException;

    /**
     * Persiste una entidad en la fuente de datos.
     *
     * <p>La implementación debe manejar si el objeto representa una inserción nueva o una actualización
     * según el estado del identificador u otra convención propia.</p>
     *
     * @param t instancia de la entidad a guardar o actualizar
     * @throws SQLException si ocurre un error en la operación de inserción/actualización
     */
    void guardar(T t) throws SQLException;

    /**
     * Elimina una entidad por su identificador.
     *
     * @param id identificador de la entidad a eliminar
     * @throws SQLException si ocurre un error al eliminar el registro
     */
    void eliminar(Long id) throws SQLException;
}