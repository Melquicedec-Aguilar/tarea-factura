package org.aguilar.webapp.factura.controllers;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.aguilar.webapp.factura.models.Producto;
import org.aguilar.webapp.factura.services.LoginService;
import org.aguilar.webapp.factura.services.ProductoService;

/**
 * Servlet que maneja las peticiones a la ruta {@code /productos}.
 *
 * <p>Este servlet prepara los datos necesarios para la vista {@code listar.jsp}:
 * obtiene la lista de productos desde {@link ProductoService}, recupera el nombre del
 * usuario autenticado mediante {@link LoginService} (como {@code Optional\<String\>})
 * y coloca los atributos en la petición para que el JSP renderice la lista y las
 * acciones disponibles según si el usuario está presente.</p>
 *
 * <p>Observación: se mantiene el atributo {@code tittle} tal como se usa en la aplicación
 * (posible tipografía intencionada o por compatibilidad con vistas existentes).</p>
 */
@WebServlet("/productos")
public class ProductoServlet extends HttpServlet {

    /**
     * Servicio de negocio para operaciones sobre {@link Producto}.
     *
     * <p>Inyectado por CDI. Se utiliza para listar y gestionar productos.</p>
     */
    @Inject
    private ProductoService service;

    /**
     * Servicio de autenticación/usuario.
     *
     * <p>Inyectado por CDI. Proporciona utilidades para obtener el nombre del usuario logueado
     * desde la petición HTTP. El valor esperado es un {@code Optional<String>}.</p>
     */
    @Inject
    private LoginService auth;

    /**
     * Procesa las peticiones HTTP GET para listar productos.
     *
     * <p>Flujo:
     * <ol>
     *   <li>Obtiene la lista de productos mediante {@code service.listar()}.</li>
     *   <li>Obtiene el nombre de usuario (si existe) mediante {@code auth.getUsername(req)}.</li>
     *   <li>Coloca en la petición los atributos esperados por la vista:
     *       {@code tipo} = "productos", {@code productos}, {@code username} y {@code tittle}.</li>
     *   <li>Realiza forward a {@code /listar.jsp} para renderizar la vista.</li>
     * </ol></p>
     *
     * @param req  petición HTTP entrante
     * @param resp respuesta HTTP
     * @throws ServletException si ocurre un error durante el forward
     * @throws IOException      si ocurre un error de entrada/salida
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Producto> productos = service.listar();

        Optional<String> usernameOptional = auth.getUsername(req);

        req.setAttribute("tipo", "productos");
        req.setAttribute("productos", productos);
        req.setAttribute("username", usernameOptional);
        // Nota: se preserva el uso de "title" para mantener compatibilidad con las vistas existentes
        req.setAttribute("title", "Listado de productos");

        getServletContext().getRequestDispatcher("/listar.jsp").forward(req, resp);
    }
}