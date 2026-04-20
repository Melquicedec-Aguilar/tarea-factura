package org.aguilar.webapp.factura.models;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class Factura {
    private Long id;
    private Integer numeroFactura;
    private String descripcion;
    private LocalDate fechaFactura;
    private List<LineaFactura> lineasFactura;
    private Usuario usuario;

    @PostConstruct
    public void inicializar() {
        this.numeroFactura = 1001;
        this.descripcion = "Factura de compra inicial";
        this.lineasFactura = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(Integer numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaFactura() {
        return fechaFactura;
    }

    public void setFechaFactura(LocalDate fechaFactura) {
        this.fechaFactura = fechaFactura;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    @Inject
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<LineaFactura> getLineasFactura() {
        return lineasFactura;
    }

    public void setLineasFactura(List<LineaFactura> lineasFactura) {
        this.lineasFactura = lineasFactura;
    }
}
