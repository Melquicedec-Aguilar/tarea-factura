package org.aguilar.webapp.factura.controllers;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import org.aguilar.webapp.factura.models.Carro;
import org.aguilar.webapp.factura.models.Factura;
import org.aguilar.webapp.factura.models.Usuario;
import org.aguilar.webapp.factura.services.FacturaService;

@WebServlet("/factura")
public class FacturaServlet extends HttpServlet {

    @Inject
    private Carro carro;

    @Inject
    private FacturaService service;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        System.out.println("Generando factura para usuario: " + usuario.getUsername());

        req.setAttribute("title", "Factura");

        if (carro != null && !carro.getItems().isEmpty()){
            Factura factura = service.crearFactura(carro, usuario);

            req.setAttribute("factura", factura);

            System.out.println("Factura generada: " + factura.getNumeroFactura()
                + " fecha=" + factura.getFechaFactura()
                + " lineas=" + factura.getLineasFactura().size());

            getServletContext().getRequestDispatcher("/factura.jsp").forward(req, resp);
        }else{
            resp.sendRedirect(req.getContextPath() + "/carro.jsp?error=Carro vacío");
        }
    }
}
