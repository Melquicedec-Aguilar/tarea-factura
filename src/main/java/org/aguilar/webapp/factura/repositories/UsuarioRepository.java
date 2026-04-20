package org.aguilar.webapp.factura.repositories;

import java.sql.SQLException;
import org.aguilar.webapp.factura.models.Usuario;

public interface UsuarioRepository extends Repository<Usuario> {
    Usuario porUsername(String username) throws SQLException;
}
