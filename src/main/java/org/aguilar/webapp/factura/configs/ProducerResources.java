package org.aguilar.webapp.factura.configs;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

@ApplicationScoped
public class ProducerResources {

    @Resource(name = "jdbc/mysqlDB")
    private DataSource ds;

    @RequestScoped
    private Connection createConnection() throws SQLException {
        return ds.getConnection();
    }
}
