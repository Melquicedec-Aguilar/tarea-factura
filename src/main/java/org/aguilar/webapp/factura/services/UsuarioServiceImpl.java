package org.aguilar.webapp.factura.services;

import jakarta.inject.Inject;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.aguilar.webapp.factura.configs.Service;
import org.aguilar.webapp.factura.models.Usuario;
import org.aguilar.webapp.factura.repositories.UsuarioRepository;

/**
 * Implementación del servicio de negocio para operaciones relacionadas con {@link Usuario}.
 *
 * <p>Responsabilidad:
 * - Orquestar llamadas al repositorio {@link UsuarioRepository} y adaptar las excepciones de
 *   acceso a datos (SQLException) a RuntimeException para simplificar la capa de presentación.
 * - Exponer operaciones de autenticación y CRUD básico sobre usuarios.</p>
 *
 * <p>Notas generales:
 * - Los SQLException lanzados por el repositorio se envuelven en RuntimeException en esta capa.
 * - La implementación asume que la validación de datos (por ejemplo, formatos de email,
 *   unicidad de username) se realiza en una capa superior o en el propio repositorio según convenga.</p>
 */
@Service
public class UsuarioServiceImpl implements UsuarioService {

    /**
     * Repositorio inyectado que realiza las operaciones de persistencia para {@link Usuario}.
     *
     * <p>Observación: la gestión de la conexión y transacciones la maneja la implementación
     * concreta del repositorio o el contenedor; este servicio delega completamente en él.</p>
     */
    @Inject
    UsuarioRepository usuarioRepository;

    /**
     * Intenta autenticar a un usuario con las credenciales proporcionadas.
     *
     * <p>Comportamiento:
     * - Busca el usuario por {@code username} mediante el repositorio.
     * - Si se encuentra, compara la contraseña almacenada con la proporcionada usando {@code equals}.</p>
     *
     * <p>Retorno:
     * - {@link Optional} con el {@link Usuario} autenticado si las credenciales coinciden.
     * - {@link Optional#empty()} si no existe el usuario o la contraseña no coincide.</p>
     *
     * <p>Consideraciones de seguridad:
     * - La comparación se realiza en texto claro tal como está implementada aquí. En producción,
     *   las contraseñas deben almacenarse en forma de hash seguro y la comparación debe realizarse
     *   comparando hashes. Evitar almacenar o comparar contraseñas en texto claro.</p>
     *
     * <p>Manejo de errores:
     * - Si el repositorio lanza {@link SQLException}, se envuelve y re-lanza como {@link RuntimeException}.</p>
     *
     * @param username nombre de usuario para la autenticación (no nulo)
     * @param password contraseña proporcionada (texto plano, la implementación debería usar hashing)
     * @return Optional con el usuario autenticado o Optional.empty() si la autenticación falla
     */
    @Override
    public Optional<Usuario> login(String username, String password) {
        try {
            return Optional.ofNullable(usuarioRepository.porUsername(username))
                .filter(u -> u.getPassword().equals(password));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Devuelve la lista de todos los usuarios.
     *
     * <p>Retorno:
     * - Lista con todos los {@link Usuario} devuelta por el repositorio; puede ser vacía si no hay usuarios.</p>
     *
     * <p>Manejo de errores:
     * - Los {@link SQLException} del repositorio se envuelven en {@link RuntimeException}.</p>
     *
     * @return lista de usuarios
     */
    @Override
    public List<Usuario> listar() {
        try {
            return usuarioRepository.listar();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Busca un usuario por su identificador.
     *
     * <p>Retorno:
     * - {@link Optional} con el {@link Usuario} si existe; {@link Optional#empty()} si no existe.</p>
     *
     * <p>Manejo de errores:
     * - Las excepciones {@link SQLException} se convierten en {@link RuntimeException}.</p>
     *
     * @param id identificador del usuario
     * @return Optional con el usuario si se encuentra
     */
    @Override
    public Optional<Usuario> porId(Long id) {
        try {
            return Optional.ofNullable(usuarioRepository.porId(id));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Persiste o actualiza la entidad {@link Usuario}.
     *
     * <p>Comportamiento:
     * - Delegación al repositorio para realizar insert/update según la implementación de {@link UsuarioRepository}.</p>
     *
     * <p>Consideraciones:
     * - Validaciones (p. ej. formato de email, longitud de password, unicidad de username) no se aplican aquí;
     *   deben realizarse antes de llamar a este método o añadirse en una futura mejora.</p>
     *
     * <p>Manejo de errores:
     * - Cualquier {@link SQLException} se convierte en {@link RuntimeException}.</p>
     *
     * @param usuario entidad a guardar o actualizar
     */
    @Override
    public void guardar(Usuario usuario) {
        try {
            usuarioRepository.guardar(usuario);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Elimina el usuario identificado por {@code id}.
     *
     * <p>Manejo de errores:
     * - Los {@link SQLException} que ocurran al eliminar se envuelven en {@link RuntimeException}.</p>
     *
     * @param id identificador del usuario a eliminar
     */
    @Override
    public void eliminar(Long id) {
        try {
            usuarioRepository.eliminar(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}