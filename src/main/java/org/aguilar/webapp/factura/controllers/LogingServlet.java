package org.aguilar.webapp.factura.controllers;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;
import org.aguilar.webapp.factura.models.Usuario;
import org.aguilar.webapp.factura.services.LoginService;
import org.aguilar.webapp.factura.services.UsuarioService;

/**
 * Servlet responsable de la funcionalidad de login de la aplicación.
 *
 * <p>Mapeado a la ruta {@code /login}. Maneja:
 * - GET: mostrar la página de login o la bienvenida si ya existe un usuario autenticado en la petición.
 * - POST: procesar las credenciales recibidas y, si la autenticación es correcta, crear la sesión
 *   (atributo "username") y redirigir al mismo endpoint para que la GET muestre la bienvenida.</p>
 *
 * <p>Notas:
 * - Este servlet delega la autenticación en {@link UsuarioService} y la obtención del username
 *   asociado a la petición en {@link LoginService} (por ejemplo, leer de la sesión o de otros orígenes).</p>
 */
@WebServlet({"/login"})
public class LogingServlet extends HttpServlet {

    /**
     * Servicio que proporciona operaciones sobre usuarios (incluye {@code login}).
     *
     * <p>Se espera que {@link UsuarioService#login(String, String)} devuelva un Optional con el usuario
     * cuando las credenciales son válidas.</p>
     */
    @Inject
    private UsuarioService service;

    /**
     * Servicio encargado de obtener el username asociado a la petición HTTP.
     *
     * <p>Por ejemplo, una implementación puede leerlo desde la sesión, desde un token JWT, o de
     * {@code request.getUserPrincipal()}. {@link #doGet} usa este servicio para determinar si el usuario
     * ya está autenticado y mostrar la vista correspondiente.</p>
     */
    @Inject
    private LoginService auth;

    /**
     * GET /login
     *
     * <p>Flujo:
     * - Intenta obtener el username a través de {@link LoginService#getUsername(HttpServletRequest)}.
     * - Si está presente, establece el atributo de request "username" y muestra {@code bienvenida.jsp}.
     * - Si no está presente, prepara la vista de login estableciendo "title" y mostrando {@code login.jsp}.</p>
     *
     * @param req petición HTTP
     * @param resp respuesta HTTP
     * @throws ServletException en caso de error del servlet
     * @throws IOException en caso de errores de E/S
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Optional<String> usernameOptional = auth.getUsername(req);

        if (usernameOptional.isPresent()){
            req.setAttribute("username", usernameOptional.get());
            getServletContext().getRequestDispatcher("/bienvenida.jsp").forward(req, resp);
        }else {
            req.setAttribute("title", "Login");
            getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
        }

        HttpSession session = req.getSession();

        System.out.println("Valor en sesión: '" + session.getAttribute("username") + "'");
    }

    /**
     * POST /login
     *
     * <p>Procesa las credenciales enviadas desde el formulario de login:
     * - Obtiene los parámetros "username" y "password".
     * - Llama a {@link UsuarioService#login(String, String)} para validar credenciales.
     * - Si la autenticación es correcta:
     *     - Crea/obtiene la sesión HTTP y guarda el atributo "username".
     *     - Redirige a {@code /login} para que la GET muestre la página de bienvenida.
     * - Si la autenticación falla, responde con 401 Unauthorized.</p>
     *
     * <p>Consideraciones de seguridad:
     * - Aquí la verificación de la contraseña la realiza {@code UsuarioService}; en producción
     *   asegúrese de que las contraseñas se comparen con hashes seguros y que no se expongan en logs.</p>
     *
     * @param req petición HTTP con los parámetros del formulario
     * @param resp respuesta HTTP usada para redirecciones o errores
     * @throws ServletException en caso de error del servlet
     * @throws IOException en caso de errores de E/S
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        Optional<Usuario> usernameOptional = service.login(username, password);

        if (usernameOptional.isPresent()){

            HttpSession session = req.getSession();
            session.setAttribute("username", username);

            resp.sendRedirect(req.getContextPath() + "/login");
        }else {
            req.getSession().removeAttribute("username");
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Lo sentimos no esta autorizado para ingresar a esta página");
        }

    }
}