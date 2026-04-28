package org.aguilar.webapp.factura.controllers;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import org.aguilar.webapp.factura.services.LoginService;

/**
 * Servlet encargado de gestionar el cierre de sesión del usuario.
 *
 * <p>Mapeado en <code>/logout</code>. Su responsabilidad principal es:
 * <ul>
 *   <li>Comprobar si existe un usuario autenticado mediante el {@link LoginService}.</li>
 *   <li>Invalidar la sesión HTTP si hay un usuario autenticado.</li>
 *   <li>Redirigir al endpoint de login después de realizar el logout.</li>
 * </ul>
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    /**
     * Servicio inyectado que provee utilidades de autenticación.
     *
     * <p>Se espera que {@link LoginService#getUsername(HttpServletRequest)} devuelva un
     * {@link Optional} con el nombre del usuario si hay una sesión/autenticación válida,
     * o {@link Optional#empty()} si no hay usuario autenticado.
     */
    @Inject
    private LoginService auth;

    /**
     * Maneja peticiones GET para cerrar la sesión del usuario.
     *
     * <p>Flujo:
     * <ol>
     *   <li>Obtener el nombre de usuario actual desde el servicio de autenticación.</li>
     *   <li>Si el usuario está presente, invalidar la sesión HTTP para eliminar atributos y
     *       datos asociados a la sesión.</li>
     *   <li>Siempre redirigir al formulario/página de login dejando la aplicación en un estado
     *       sin sesión.</li>
     * </ol>
     *
     * @param req  petición HTTP entrante
     * @param resp respuesta HTTP usada para redirección
     * @throws ServletException si ocurre un error de servlet
     * @throws IOException      si ocurre un error de E/S al redirigir
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Intentar obtener el nombre de usuario autentificado (puede estar vacío)
        Optional<String> username = auth.getUsername(req);

        // Si hay un usuario presente, invalidar la sesión para cerrar sesión completamente
        if (username.isPresent()){
            req.getSession().invalidate();
        }

        // Redirigir siempre a la página de login para que el usuario pueda autenticarse nuevamente
        resp.sendRedirect(req.getContextPath() + "/login");
    }
}