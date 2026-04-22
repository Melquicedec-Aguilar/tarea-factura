package org.aguilar.webapp.factura.configs;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.spi.InjectionPoint;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;
import javax.sql.DataSource;

@ApplicationScoped
public class ProducerResources {

    @Resource(name = "jdbc/mysqlDB")
    private DataSource ds;

    @Produces
    @RequestScoped
    private Connection createConnection() throws SQLException {
        return ds.getConnection();
    }

    public void close(@Disposes Connection connection) throws SQLException {
        connection.close();
    }

    @Produces
    private Logger beanLogger(InjectionPoint injectionPoint){
        return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
    }
}
