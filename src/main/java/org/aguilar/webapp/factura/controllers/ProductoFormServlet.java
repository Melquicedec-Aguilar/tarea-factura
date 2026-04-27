package org.aguilar.webapp.factura.controllers;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.aguilar.webapp.factura.models.Categoria;
import org.aguilar.webapp.factura.models.Producto;
import org.aguilar.webapp.factura.services.ProductoService;

/**
 * Servlet responsable de servir el formulario de creación/edición de productos y
 * de procesar el envío del formulario.
 *
 * <p>Mapeado en <code>/productos/form</code>. Funcionalidad principal:
 * <ul>
 *   <li>doGet: cargar listas necesarias (categorías), obtener producto por id (si existe)
 *       y forward a la JSP del formulario.</li>
 *   <li>doPost: leer parámetros del formulario, validar, convertir tipos (fecha, id, precio),
 *       construir entidad Producto y delegar en el servicio para persistir o reenviar
 *       al formulario con errores.</li>
 * </ul>
 */
@WebServlet("/productos/form")
public class ProductoFormServlet extends HttpServlet {

    /**
     * Servicio inyectado que provee operaciones sobre productos y categorías.
     */
    @Inject
    private ProductoService service;

    /**
     * Maneja peticiones GET para mostrar el formulario.
     *
     * <p>Flujo:
     * <ol>
     *   <li>Intentar leer parámetro "id" y parsearlo a Long (si falla se asume 0).</li>
     *   <li>Si id > 0, obtener el producto desde el servicio.</li>
     *   <li>Asegurar que producto.categoria no sea {@code null} (evita NPE en la JSP).</li>
     *   <li>Poner en request las listas/objetos necesarios y hacer forward a /form.jsp.</li>
     * </ol>
     *
     * @param req  objeto HttpServletRequest con parámetros de la petición
     * @param resp objeto HttpServletResponse para responder al cliente
     * @throws ServletException si ocurre un error en el servlet
     * @throws IOException      si ocurre un error de E/S
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Long id;
        try {
            // Intento de leer y convertir el parámetro "id". Si no viene o es inválido, se usa 0L.
            id = Long.valueOf(req.getParameter("id"));
        } catch (NumberFormatException e) {
            id = 0L;
        }

        // Crear un producto por defecto. Si se carga uno desde el servicio se sobrescribirá.
        Producto producto = new Producto();
        // Inicializar categoría para evitar null en la JSP al acceder a producto.categoria.id
        producto.setCategoria(new Categoria());

        if (id > 0) {
            // Si hay id válido, intentar obtener el producto desde el servicio
            Optional<Producto> o = service.porId(id);
            if (o.isPresent()) {
                producto = o.get();
            }
        }

        // Preparar atributos necesarios para la vista JSP
        req.setAttribute("tipo", "productos");
        req.setAttribute("categorias", service.listarCategoria());
        req.setAttribute("producto", producto);
        req.setAttribute("title", "Formulario productos");

        // Forward al JSP del formulario
        getServletContext().getRequestDispatcher("/form.jsp").forward(req, resp);
    }

    /**
     * Maneja peticiones POST para procesar el envío del formulario de producto.
     *
     * <p>Flujo:
     * <ul>
     *   <li>Leer parámetros simples (nombre, precio, sku, fecha_registro, categoria, id).</li>
     *   <li>Parsear/convertir valores a los tipos adecuados con manejo de excepciones.</li>
     *   <li>Validar campos requeridos y acumular errores en un mapa.</li>
     *   <li>Intentar parsear la fecha con un patrón (si falla, queda null).</li>
     *   <li>Construir objeto Producto y, si no hay errores, guardarlo mediante el servicio
     *       y redirigir a la lista; si hay errores, reenviar a la vista con los mensajes.</li>
     * </ul>
     *
     * @param req  objeto HttpServletRequest con parámetros del formulario
     * @param resp objeto HttpServletResponse para redirecciones/respuestas
     * @throws ServletException si ocurre un error en el servlet
     * @throws IOException      si ocurre un error de E/S
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Lectura de parámetros del formulario
        String nombre = req.getParameter("nombre");

        Integer precio;
        try {
            precio = Integer.valueOf(req.getParameter("precio"));
        } catch (NumberFormatException e) {
            // Si no es convertible, usar 0 como indicativo de valor inválido
            precio = 0;
        }

        String sku = req.getParameter("sku");

        String fechaStr = req.getParameter("fecha_registro");

        Long categoriaId;
        try {
            categoriaId = Long.valueOf(req.getParameter("categoria"));
        } catch (NumberFormatException e) {
            // Si no viene o es inválido, usar 0L como indicativo de no seleccionado
            categoriaId = 0L;
        }

        // Validaciones simples acumuladas en un mapa (clave -> mensaje)
        Map<String, String> errores = new HashMap<>();
        if (nombre == null || nombre.isBlank()) {
            errores.put("nombre", "El nombre es requerido");
        }
        if (sku == null || sku.isBlank()) {
            errores.put("sku", "El sku es requerido");
        }
        if (fechaStr == null || fechaStr.isBlank()) {
            errores.put("fecha_registro", "La fecha de registro es requerida");
        }
        if (precio.equals(0)) {
            errores.put("precio", "El precio es requerido");
        }
        if (categoriaId.equals(0L)) {
            errores.put("categoria", "La categoria es requerida");
        }

        // Conversión de la fecha: se usa un patrón "yyy-MM-dd" (tener en cuenta que lo habitual es "yyyy-MM-dd")
        LocalDate fecha;
        try {
            fecha = LocalDate.parse(fechaStr, DateTimeFormatter.ofPattern("yyy-MM-dd"));
        } catch (DateTimeParseException e) {
            // En caso de formato inválido, dejar fecha en null (y la validación anterior puede cubrir ausencia)
            fecha = null;
        }

        Long id;
        try {
            id = Long.valueOf(req.getParameter("id"));
        } catch (NumberFormatException e) {
            id = 0L;
        }

        // Construcción del objeto Producto con los valores leídos
        Producto producto = new Producto();
        producto.setId(id);
        producto.setNombre(nombre);
        producto.setSku(sku);
        producto.setPrecio(precio);
        producto.setFechaRegistro(fecha);

        // Asignar sólo la referencia de categoría por id (la entidad completa puede resolverse en el servicio/DAO)
        Categoria categoria = new Categoria();
        categoria.setId(categoriaId);
        producto.setCategoria(categoria);

        if (errores.isEmpty()) {
            // Si no hay errores, persistir y redirigir a la lista de productos
            service.guardar(producto);
            resp.sendRedirect(req.getContextPath() + "/productos");
        } else {
            // Si hay errores, reenviar al formulario con los mensajes y datos parcialmente construidos
            req.setAttribute("tipo", "productos");
            req.setAttribute("errores", errores);
            req.setAttribute("categorias", service.listarCategoria());
            req.setAttribute("producto", producto);
            req.setAttribute("title", "Formulario de productos");

            getServletContext().getRequestDispatcher("/form.jsp").forward(req, resp);
        }
    }
}