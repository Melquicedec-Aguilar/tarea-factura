package org.aguilar.webapp.factura.controllers;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import org.aguilar.webapp.factura.models.Carro;
import org.aguilar.webapp.factura.models.ItemCarro;
import org.aguilar.webapp.factura.models.Producto;
import org.aguilar.webapp.factura.services.ProductoService;

@WebServlet("/agregar-carro")
public class AgregarCarro extends HttpServlet {

    @Inject
    private ProductoService service;

    @Inject
    private Carro carro;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("id"));

        Optional<Producto> producto= service.porId(id);
        if(producto.isPresent()){
            ItemCarro item = new ItemCarro(1, producto.get());
            carro.addItemCarro(item);
        }
        resp.sendRedirect(req.getContextPath() + "/ver-carro");
    }
}
