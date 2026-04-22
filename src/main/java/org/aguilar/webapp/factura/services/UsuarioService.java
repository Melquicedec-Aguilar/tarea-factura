package org.aguilar.webapp.factura.services;

import java.util.List;
import java.util.Optional;
import org.aguilar.webapp.factura.models.Usuario;

/**
 * Servicio que define las operaciones de negocio relacionadas con {@link Usuario}.
 *
 * <p>Responsabilidad:
 * - Exponer las operaciones necesarias para autenticación y gestión básica de usuarios
 *   (listado, consulta por id, guardar y eliminar).</p>
 *
 * <p>Contrato general:
 * - Implementaciones deben encargarse de la validación necesaria, control de transacciones
 *   y traducción de errores de persistencia a excepciones apropiadas (unchecked o checked
 *   según la política de la aplicación).</p>
 */
public interface UsuarioService {

    /**
     * Intenta autenticar (login) un usuario con las credenciales proporcionadas.
     *
     * <p>Comportamiento esperado:
     * - Si las credenciales son correctas, devuelve un {@link Optional} con el {@link Usuario}.
     * - Si no existen las credenciales o son incorrectas, devuelve {@link Optional#empty()}.
     *
     * @param username nombre de usuario (no nulo)
     * @param password contraseña en texto plano para validar (la implementación debe manejar el hashing/seguridad)
     * @return {@link Optional} con el usuario autenticado, o {@code Optional.empty()} si la autenticación falla
     */
    Optional<Usuario> login(String username, String password);

    /**
     * Devuelve todos los usuarios disponibles.
     *
     * @return lista con todos los {@link Usuario}; puede ser una lista vacía si no hay usuarios
     */
    List<Usuario> listar();

    /**
     * Busca y devuelve un usuario por su identificador.
     *
     * @param id identificador del usuario a buscar
     * @return {@link Optional} con el {@link Usuario} si existe, o {@code Optional.empty()} si no se encuentra
     */
    Optional<Usuario> porId(Long id);

    /**
     * Persiste un {@link Usuario}. El comportamiento (insertar o actualizar) depende
     * de la implementación y del estado del objeto (por ejemplo, si {@code usuario.getId() == null}
     * puede interpretarse como un insert).
     *
     * <p>La implementación debe validar los datos necesarios (por ejemplo, unicidad de username)
     * y lanzar la excepción adecuada en caso de fallo.</p>
     *
     * @param usuario entidad de usuario a guardar o actualizar
     */
    void guardar(Usuario usuario);

    /**
     * Elimina el usuario identificado por {@code id}.
     *
     * @param id identificador del usuario a eliminar
     */
    void eliminar(Long id);
}