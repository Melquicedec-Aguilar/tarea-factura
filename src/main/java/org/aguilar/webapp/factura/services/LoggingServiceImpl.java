package org.aguilar.webapp.factura.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;
import org.aguilar.webapp.factura.configs.Service;

/**
 * Implementación simple de {@link LoginService} que obtiene el nombre de usuario
 * de la sesión HTTP.
 *
 * <p>Responsabilidad:
 * - Extraer el username asociado a la petición HTTP desde un atributo de sesión
 *   (atributo con clave "username") y devolverlo envuelto en un {@link Optional}.</p>
 *
 * <p>Notas y consideraciones:
 * - Esta implementación asume que algún punto anterior en la aplicación (por ejemplo,
 *   el proceso de autenticación) ha almacenado el nombre de usuario en la sesión:
 *   {@code session.setAttribute("username", username)}.
 * - Si no existe el atributo o su valor es null, se devuelve {@link Optional#empty()}.
 * - No realiza validaciones adicionales ni efectos secundarios sobre la sesión.</p>
 */
@Service
public class LoggingServiceImpl implements LoginService{
    /**
     * Intenta obtener el nombre de usuario asociado a la petición HTTP.
     *
     * <p>Comportamiento:
     * - Recupera la sesión con {@code request.getSession()} y lee el atributo "username".
     * - Si el atributo existe y no es {@code null}, devuelve {@link Optional} con su valor.
     * - En caso contrario devuelve {@link Optional#empty()}.</p>
     *
     * <p>Parámetros:
     * @param request la {@link HttpServletRequest} de la cual extraer la sesión (no debe ser {@code null})
     *
     * Retorno:
     * @return {@link Optional} con el username si está presente en la sesión; {@code Optional.empty()} si no.</p>
     *
     * <p>Mejoras posibles:
     * - Extraer la clave del atributo ("username") a una constante centralizada para evitar duplicación.
     * - Soportar otros orígenes (cabeceras, JWT, Principal) mediante una estrategia configurable.
     * - Evitar la creación de una nueva sesión si no existe (usar {@code request.getSession(false)})
     *   si no se desea crear sesiones implícitas al consultar el username.</p>
     */
    @Override
    public Optional<String> getUsername(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        if (username != null){
            return Optional.of(username);
        }
        return Optional.empty();
    }
}