package org.aguilar.webapp.factura.controllers;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.aguilar.webapp.factura.models.Usuario;
import org.aguilar.webapp.factura.services.UsuarioService;

@WebServlet("/usuarios/form")
public class UsuarioFormServlet extends HttpServlet {

    @Inject
    private UsuarioService service;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Long id;
        try {
            id = Long.valueOf(req.getParameter("id"));
        } catch (NumberFormatException e){
            id = 0L;
        }

        Usuario usuario = new Usuario();
        if(id > 0){
            Optional<Usuario> u = service.porId(id);
            if (u.isPresent()){
                usuario = u.get();
            }
        }

        req.setAttribute("tipo", "usuarios");
        req.setAttribute("usuario", usuario);
        req.setAttribute("tittle", "Formulario de usuarios");

        getServletContext().getRequestDispatcher("/form.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");

        Map<String, String> errores = new HashMap<>();
        if (username == null || username.isBlank()) {
            errores.put("username", "El username es requerido");
        }
        if (password == null || password.isBlank()) {
            errores.put("password", "El password es requerido");
        }
        if (email == null || email.isBlank()) {
            errores.put("email", "El email es requerido");
        }

        Long id;
        try {
            id = Long.valueOf(req.getParameter("id"));
        } catch (NumberFormatException e) {
            id = 0L;
        }

        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setUsername(username);
        usuario.setPassword(password);
        usuario.setEmail(email);

        if (errores.isEmpty()){
            service.guardar(usuario);

            // Si username es válido, almacenarlo en la sesión para que bienvenida.jsp pueda mostrarlo.
            if (username != null && !username.isBlank()) {
                // obtener/crear sesión y asignar atributo username (no guardar la cadena "null")
                req.getSession().setAttribute("username", username);
            } else {
                // asegurar que no quede una cadena "null" en la sesión
                if (req.getSession() != null) {
                    req.getSession().removeAttribute("username");
                }
            }

            resp.sendRedirect(req.getContextPath() + "/bienvenida.jsp");
        } else {
            req.setAttribute("tipo", "usuarios");
            req.setAttribute("usuario", usuario);
            req.setAttribute("errores", errores);
            // establecer título sensible evitando concatenaciones con null
            req.setAttribute("tittle", "Formulario de usuarios");

            getServletContext().getRequestDispatcher("/form.jsp").forward(req, resp);
        }
    }
}