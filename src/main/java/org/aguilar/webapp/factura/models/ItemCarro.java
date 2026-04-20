package org.aguilar.webapp.factura.models;

import java.util.Objects;

public class ItemCarro {
    private int cantidad;
    private Producto producto;

    public ItemCarro(int cantidad, Producto producto) {
        this.cantidad = cantidad;
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getImporte(){
        return cantidad * producto.getPrecio();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        ItemCarro itemCarro = (ItemCarro) object;
        return cantidad == itemCarro.cantidad && Objects.equals(producto, itemCarro.producto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cantidad, producto);
    }
}
