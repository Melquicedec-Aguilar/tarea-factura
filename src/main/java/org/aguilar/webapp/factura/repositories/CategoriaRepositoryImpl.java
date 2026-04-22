package org.aguilar.webapp.factura.repositories;

import jakarta.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.aguilar.webapp.factura.configs.Repositorio;
import org.aguilar.webapp.factura.models.Categoria;

@Repositorio
public class CategoriaRepositoryImpl implements Repository<Categoria> {

    @Inject
    private Connection conn;

    @Override
    public List<Categoria> listar() throws SQLException {
        List<Categoria> categoria = new ArrayList<>();

        try (Statement stmt = conn.createStatement();
        var rs = stmt.executeQuery("SELECT * FROM categorias")){
            while (rs.next()){

            }
        }
        return List.of();
    }

    @Override
    public Categoria porId(Long id) throws SQLException {
        Categoria categoria = null;

        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM categoria WHERE id =?")){
            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()){
                if (rs.next()){
                    categoria = getCategoria(rs);
                }
            }
        }
        return categoria;
    }

    @Override
    public void guardar(Categoria categoria) throws SQLException {

    }

    @Override
    public void eliminar(Long id) throws SQLException {

    }

    private Categoria getCategoria(ResultSet rs) throws SQLException {
        Categoria categoria = new Categoria();
        categoria.setId(rs.getLong("id"));
        categoria.setNombre(rs.getString("nombre"));
        return categoria;
    }
}
