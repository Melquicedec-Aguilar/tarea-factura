package org.aguilar.webapp.factura.services;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Servicio que abstrae la obtención del nombre de usuario asociado a una solicitud HTTP.
 *
 * <p>Responsabilidad:
 * - Proveer una manera centralizada y testable de extraer el identificador/username del actor
 *   que realizó la petición {@link HttpServletRequest}.
 * - Permitir distintas implementaciones (por ejemplo: extraer de la sesión, de un atributo,
 *   de un encabezado, de un token JWT, o de {@code request.getUserPrincipal()}).</p>
 *
 * <p>Contratos y consideraciones:
 * - El método {@link #getUsername(HttpServletRequest)} no debe causar efectos secundarios
 *   inesperados sobre la {@code HttpServletRequest} (por ejemplo, no debe modificar atributos
 *   salvo que la implementación lo documente explícitamente).
 * - Devuelve un {@link Optional} para expresar de forma explícita que puede no existir un
 *   username asociado a la petición (usuario no autenticado).</p>
 */
public interface LoginService {
    /**
     * Intenta obtener el nombre de usuario asociado a la petición HTTP.
     *
     * <p>Comportamiento esperado:
     * - Si la petición está asociada a un usuario autenticado, devuelve {@code Optional} con el username.
     * - Si no hay usuario autenticado o no se puede extraer el username, devuelve {@link Optional#empty()}.</p>
     *
     * <p>Ejemplos de orígenes del username:
     * - {@code request.getUserPrincipal().getName()}
     * - Un atributo en sesión/solicitud (p. ej. {@code request.getSession().getAttribute("username")})
     * - Un encabezado HTTP o un token (p. ej. extraer de Authorization / JWT)</p>
     *
     * @param request la {@link HttpServletRequest} desde la que se extrae el username (no nulo)
     * @return {@link Optional} con el username si está presente; {@code Optional.empty()} si no existe
     */
    Optional<String> getUsername(HttpServletRequest request);
}