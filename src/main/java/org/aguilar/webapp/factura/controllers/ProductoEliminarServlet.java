package org.aguilar.webapp.factura.controllers;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import org.aguilar.webapp.factura.models.Producto;
import org.aguilar.webapp.factura.services.ProductoService;

/**
 * Servlet encargado de eliminar un {@link Producto}.
 *
 * <p>Mapeado en <code>/productos/eliminar</code>. Lee el parámetro {@code id} de la
 * petición GET, valida su conversión a {@code Long}, verifica que el producto exista
 * y delega la eliminación al {@link ProductoService}. Responde con redirección a la
 * lista de productos o con error 404 si el id es inválido o el producto no existe.
 */
@WebServlet("/productos/eliminar")
public class ProductoEliminarServlet extends HttpServlet {

    /**
     * Servicio inyectado que provee operaciones sobre productos.
     */
    @Inject
    private ProductoService service;

    /**
     * Maneja peticiones GET para eliminar un producto.
     *
     * <p>Flujo:
     * <ol>
     *   <li>Leer y convertir el parámetro {@code id} a {@code Long} (si falla se asume 0L).</li>
     *   <li>Si el id es válido (> 0), buscar el producto; si existe, eliminarlo y redirigir.</li>
     *   <li>Si no existe, responder con 404 Not Found con mensaje descriptivo.</li>
     *   <li>Si el id es inválido o no fue enviado, responder con 404 indicando el problema.</li>
     * </ol>
     *
     * @param req  petición HTTP entrante que debe contener el parámetro {@code id}
     * @param resp respuesta HTTP usada para redirección o envío de errores
     * @throws ServletException si ocurre un error de servlet
     * @throws IOException      si ocurre un error de E/S al redirigir o escribir la respuesta
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Long id;
        try {
            // Intentar convertir el parámetro "id" a Long.
            // Si el parámetro es null o no es numérico, se lanzará NumberFormatException.
            id = Long.valueOf(req.getParameter("id"));
        } catch (NumberFormatException e) {
            // Cuando falla la conversión, se trata como id inválido (0L).
            id = 0L;
        }

        if (id > 0) {
            // Buscar el producto por id antes de eliminar para validar existencia.
            Optional<Producto> o = service.porId(id);
            if (o.isPresent()) {
                // Si existe, delegar la eliminación al servicio y redirigir a la lista.
                service.eliminar(id);
                resp.sendRedirect(req.getContextPath() + "/productos");
            } else {
                // Producto no encontrado: responder con 404 y mensaje claro.
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "No existe el producto que desea eliminar!");
            }
        } else {
            // Id inválido o no proporcionado: responder con 404 indicando el error.
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Error: El id es null, se debe enviar como parámetro en la url!!");
        }
    }
}