package org.aguilar.webapp.factura.models;


public class LineaFactura {
    private Integer id;
    private Producto producto;
    private Integer cantidad;
    private Double precio;

    public LineaFactura() {
    }

    public LineaFactura(Producto producto, Integer cantidad, Double precio) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precio = (double) (producto.getPrecio() * cantidad);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }
}
