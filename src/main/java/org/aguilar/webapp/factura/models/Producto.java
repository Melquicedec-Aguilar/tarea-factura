package org.aguilar.webapp.factura.models;

import java.time.LocalDate;
import java.util.Objects;

public class Producto {
    private Long id;
    private String nombre;
    private Categoria categoria;
    private int precio;
    private String sku;
    private LocalDate fechaRegistro;

    public Producto() {
    }

    public Producto(Long id, int precio, String nombre, String tipo) {
        this.id = id;
        this.precio = precio;
        this.nombre = nombre;
        Categoria categoria = new Categoria();
        categoria.setNombre(tipo);
        this.categoria = categoria;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Producto producto = (Producto) object;
        return precio == producto.precio && Objects.equals(id, producto.id) && Objects.equals(nombre, producto.nombre) && Objects.equals(categoria, producto.categoria) && Objects.equals(sku, producto.sku) && Objects.equals(fechaRegistro, producto.fechaRegistro);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, categoria, precio, sku, fechaRegistro);
    }
}
