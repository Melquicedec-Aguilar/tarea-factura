package org.aguilar.webapp.factura.models;

import java.util.Objects;

/**
 * Representa una línea del carrito de compras: un {@code Producto} junto a una {@code cantidad}.
 *
 * <p>Responsabilidad:
 * - Mantener la cantidad de unidades de un producto y calcular el importe (precio * cantidad).
 * - Proveer igualdad y código hash basados en los campos actuales.</p>
 *
 * <p>Notas importantes:
 * - {@link #getImporte()} devuelve un {@code int} resultante de multiplicar cantidad por
 *   {@code producto.getPrecio()}; por convención en este proyecto los precios parecen ser
 *   enteros (por ejemplo, en centavos) — confirme la unidad usada para evitar problemas
 *   de precisión/representación de dinero.
 * - El método {@link #equals(Object)} incluye {@code cantidad} en la comparación. Esto hace
 *   que dos items con el mismo producto pero distinta cantidad NO sean considerados iguales.
 *   Si la intención es que la identidad del item esté basada únicamente en el producto,
 *   considere redefinir {@code equals} y {@code hashCode} para usar únicamente {@code producto}.</p>
 */
public class ItemCarro {
    /**
     * Cantidad de unidades del producto en esta línea del carrito.
     */
    private int cantidad;

    /**
     * Producto asociado a esta línea.
     */
    private Producto producto;

    /**
     * Crea un nuevo {@code ItemCarro} con la cantidad y producto indicados.
     *
     * @param cantidad cantidad inicial (debe ser >= 0 según validaciones de negocio externas)
     * @param producto producto asociado; se asume que no es {@code null}
     */
    public ItemCarro(int cantidad, Producto producto) {
        this.cantidad = cantidad;
        this.producto = producto;
    }

    /**
     * Devuelve la cantidad de unidades.
     *
     * @return la cantidad actual
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Establece la cantidad de unidades.
     *
     * <p>Nota: no se valida aquí el valor (p. ej. que sea >= 0). Si la lógica de negocio requiere
     * validación, hágala antes de llamar a este setter o añádala aquí.</p>
     *
     * @param cantidad nueva cantidad a establecer
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Devuelve el producto asociado a esta línea.
     *
     * @return el producto (puede considerarse que no debe ser {@code null})
     */
    public Producto getProducto() {
        return producto;
    }

    /**
     * Establece el producto asociado a esta línea.
     *
     * @param producto nuevo producto
     */
    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    /**
     * Calcula el importe de esta línea multiplicando la cantidad por el precio del producto.
     *
     * <p>Ejemplo: si {@code cantidad = 2} y {@code producto.getPrecio() = 150}, el importe será 300.</p>
     *
     * @return el importe (cantidad * precio del producto) como {@code int}
     */
    public int getImporte(){
        return cantidad * producto.getPrecio();
    }

    /**
     * Comprueba la igualdad entre dos {@code ItemCarro}.
     *
     * <p>Implementación actual: dos {@code ItemCarro} son iguales si tienen la misma
     * {@code cantidad} y su {@code producto} es igual según {@code Producto.equals}.</p>
     *
     * <p>Implicación práctica: si el modelo de carrito (por ejemplo, la clase {@code Carro})
     * quiere agrupar items por producto y sumar cantidades cuando se añade el mismo producto,
     * la inclusión de {@code cantidad} en esta comparación puede impedirlo, porque
     * {@code new ItemCarro(1, productoX)} y {@code new ItemCarro(2, productoX)} no serán
     * considerados iguales. Revise esta semántica según el comportamiento deseado.</p>
     *
     * @param object objeto a comparar
     * @return {@code true} si ambos objetos representan la misma combinación de cantidad y producto
     */
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

    /**
     * Código hash coherente con {@link #equals(Object)}.
     *
     * @return hash calculado a partir de {@code cantidad} y {@code producto}
     */
    @Override
    public int hashCode() {
        return Objects.hash(cantidad, producto);
    }
}