package org.aguilar.webapp.factura.models;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.logging.Logger;

@Named
@SessionScoped
public class Usuario implements Serializable {
    private Long id;
    private String username;
    private String password;
    private String email;

    @Inject
    private transient Logger log;

    public Usuario() {    }

    @PostConstruct
    public void init(){
        this.username = "";
        this.email = "";
        log.info(" ---------- Bean Usuario listo para Login ");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
