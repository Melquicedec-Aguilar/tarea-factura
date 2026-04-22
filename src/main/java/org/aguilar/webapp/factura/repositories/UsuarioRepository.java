package org.aguilar.webapp.factura.repositories;

import java.sql.SQLException;
import org.aguilar.webapp.factura.models.Usuario;

/**
 * Repositorio específico para la entidad {@link Usuario}.
 *
 * <p>Responsabilidad:
 * - Define operaciones de acceso a datos específicas para usuarios además de las operaciones
 *   generales provistas por {@code Repository<Usuario>} (por ejemplo: guardar, eliminar,
 *   buscar por id, listar, etc.).</p>
 *
 * <p>Contrato y consideraciones:
 * - Implementaciones concretas deben encargarse de la conexión y manejo de la fuente de datos
 *   (JDBC, JPA, etc.) y traducir errores de acceso a datos lanzando {@link SQLException}
 *   cuando corresponda.
 * - El parámetro {@code username} se espera no sea {@code null}; en caso de recibir {@code null},
 *   la implementación puede lanzar una excepción o devolver {@code null} según su política,
 *   pero es recomendable validar previamente.</p>
 */
public interface UsuarioRepository extends Repository<Usuario> {
    /**
     * Busca y devuelve el usuario cuyo nombre de usuario (username) coincide con el argumento.
     *
     * <p>Comportamiento esperado:
     * - Si existe un usuario con el username indicado, devuelve la instancia {@link Usuario}.
     * - Si no existe, puede devolver {@code null} (o lanzar una excepción según la implementación).
     *
     * @param username nombre de usuario a buscar (no debe ser {@code null})
     * @return el {@link Usuario} encontrado, o {@code null} si no existe ninguno con ese username
     * @throws SQLException si ocurre un error de acceso/consulta a la fuente de datos
     */
    Usuario porUsername(String username) throws SQLException;
}