// java
package org.aguilar.webapp.factura.models;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Representa un producto del catálogo de la aplicación.
 *
 * <p>Contiene identificador, nombre, categoría, precio, SKU y fecha de registro.
 * Esta clase es un POJO utilizado en las capas de negocio y presentación.</p>
 *
 * <p>Notas:
 * - No es una entidad JPA por defecto; si se usa JPA se deben añadir las anotaciones correspondientes
 *   (@Entity, @Id, etc.) y ajustar el tipo de {@code precio} si se requiere precisión decimal.</p>
 */
public class Producto {
    /**
     * Identificador único del producto.
     */
    private Long id;

    /**
     * Nombre legible del producto.
     */
    private String nombre;

    /**
     * Categoría a la que pertenece el producto.
     */
    private Categoria categoria;

    /**
     * Precio del producto en la unidad menor (por ejemplo, centavos) o en la unidad entera según convención del proyecto.
     *
     * <p>Nota: considerar usar {@code BigDecimal} para manejar precios con precisión decimal en aplicaciones reales.</p>
     */
    private int precio;

    /**
     * Código SKU del producto (identificador comercial).
     */
    private String sku;

    /**
     * Fecha en la que el producto fue registrado en el sistema.
     */
    private LocalDate fechaRegistro;

    /**
     * Constructor por defecto.
     */
    public Producto() {
    }

    /**
     * Constructor de conveniencia para crear un producto con valores básicos.
     *
     * @param id     identificador del producto
     * @param precio precio del producto
     * @param nombre nombre del producto
     * @param tipo   nombre de la categoría que se asignará al producto (se crea una instancia de {@link Categoria})
     */
    public Producto(Long id, int precio, String nombre, String tipo) {
        this.id = id;
        this.precio = precio;
        this.nombre = nombre;
        Categoria categoria = new Categoria();
        categoria.setNombre(tipo);
        this.categoria = categoria;
    }

    /**
     * Obtiene el identificador del producto.
     *
     * @return id del producto
     */
    public Long getId() {
        return id;
    }

    /**
     * Asigna el identificador del producto.
     *
     * @param id identificador a asignar
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del producto.
     *
     * @return nombre del producto
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Asigna el nombre del producto.
     *
     * @param nombre nombre a asignar
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la categoría del producto.
     *
     * @return categoría asociada
     */
    public Categoria getCategoria() {
        return categoria;
    }

    /**
     * Asigna la categoría del producto.
     *
     * @param categoria instancia de {@link Categoria} a asignar
     */
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    /**
     * Obtiene el precio del producto.
     *
     * @return precio (ver convención del proyecto sobre la unidad)
     */
    public int getPrecio() {
        return precio;
    }

    /**
     * Asigna el precio del producto.
     *
     * @param precio precio a asignar
     */
    public void setPrecio(int precio) {
        this.precio = precio;
    }

    /**
     * Obtiene el SKU del producto.
     *
     * @return sku del producto
     */
    public String getSku() {
        return sku;
    }

    /**
     * Asigna el SKU del producto.
     *
     * @param sku sku a asignar
     */
    public void setSku(String sku) {
        this.sku = sku;
    }

    /**
     * Obtiene la fecha de registro del producto.
     *
     * @return fecha de registro
     */
    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    /**
     * Asigna la fecha de registro del producto.
     *
     * @param fechaRegistro fecha a asignar
     */
    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    /**
     * Compara este producto con otro objeto para igualdad.
     *
     * <p>Se consideran iguales los productos que coinciden en {@code id}, {@code nombre},
     * {@code categoria}, {@code precio}, {@code sku} y {@code fechaRegistro}.</p>
     *
     * @param object objeto a comparar
     * @return {@code true} si son iguales, {@code false} en caso contrario
     */
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

    /**
     * Calcula el código hash basado en los campos relevantes.
     *
     * @return código hash del producto
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, categoria, precio, sku, fechaRegistro);
    }
}