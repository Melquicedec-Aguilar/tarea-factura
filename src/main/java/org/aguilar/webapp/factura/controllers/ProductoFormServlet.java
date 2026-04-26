package org.aguilar.webapp.factura.controllers;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import org.aguilar.webapp.factura.models.Categoria;
import org.aguilar.webapp.factura.models.Producto;
import org.aguilar.webapp.factura.services.ProductoService;

@WebServlet("/productos/form")
public class ProductoFormServlet extends HttpServlet {

    @Inject
    private ProductoService service;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Long id;
        try {
            id = Long.valueOf(req.getParameter("id"));
        }catch (NumberFormatException e){
            id = 0L;
        }

        Producto producto = new Producto();
        producto.setCategoria(new Categoria());
        if (id > 0){
            Optional<Producto> o = service.porId(id);
            if (o.isPresent()){
                producto = o.get();
            }
        }

        req.setAttribute("tipo", "productos");
        req.setAttribute("categorias", service.listarCategoria());
        req.setAttribute("producto", producto);
        req.setAttribute("title", "Listado de productos");

        getServletContext().getRequestDispatcher("/form.jsp").forward(req, resp);
    }
}
