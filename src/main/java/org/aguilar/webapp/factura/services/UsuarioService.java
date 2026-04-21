package org.aguilar.webapp.factura.services;

import java.util.List;
import java.util.Optional;
import org.aguilar.webapp.factura.models.Usuario;

public interface UsuarioService {
    Optional<Usuario> login(String username, String password);
    List<Usuario> listar();
    Optional<Usuario> porId(Long id);
    void guardar(Usuario usuario);
    void eliminar(Long id);
}
