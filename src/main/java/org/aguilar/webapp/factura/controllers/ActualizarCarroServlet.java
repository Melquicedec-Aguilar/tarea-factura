package org.aguilar.webapp.factura.controllers;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import org.aguilar.webapp.factura.models.Carro;

@WebServlet("/actualizar-carro")
public class ActualizarCarroServlet extends HttpServlet {

    @Inject
    private Carro carro;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        carro.getItems().forEach(i -> {
            Long id = i.getProducto().getId();
            String param = req.getParameter("cant_" + id);
            if (param != null){
                int nuevaCantidad = Integer.parseInt(param);
                carro.updateCarro(id, nuevaCantidad);
            }
        });

        String[] idEliminar = req.getParameterValues("deleteProductos");
        if (idEliminar != null){
            Arrays.stream(idEliminar)
                .map(i -> Long.valueOf(i))
                .forEach(id -> carro.deleteProducto(id));
        }

        resp.sendRedirect(req.getContextPath() + "/ver-carro");
    }
}
