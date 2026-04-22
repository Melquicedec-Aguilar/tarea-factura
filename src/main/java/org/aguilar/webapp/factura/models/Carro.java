package org.aguilar.webapp.factura.models;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Inject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import org.aguilar.webapp.factura.configs.CarroCompra;

/**
 * Representa un carrito de compras en memoria manejado por CDI.
 *
 * <p>Responsabilidad:
 * - Mantener una lista de {@code ItemCarro} y exponer operaciones para añadir,
 *   eliminar, actualizar y calcular el total.
 * - Está pensado para ser un bean gestionado por CDI (anotado con {@code @CarroCompra}),
 *   con métodos de inicialización y destrucción marcados con {@code @PostConstruct}
 *   y {@code @PreDestroy} respectivamente.</p>
 *
 * <p>Notas importantes:
 * - La clase implementa {@code Serializable} porque puede ser almacenada en sesión.
 *   Si la serialización entre versiones es relevante, considere declarar {@code serialVersionUID}.
 * - El logger se marca {@code transient} porque no debe serializarse.</p>
 *
 * <p>Dependencias implícitas:
 * - El comportamiento de {@link #addItemCarro(ItemCarro)} depende de que
 *   {@code ItemCarro.equals(...)} compare la identidad del producto (no la cantidad).
 * - Muchos métodos asumen que {@code ItemCarro.getProducto()} y {@code Producto.getId()}
 *   no devuelven {@code null}.</p>
 */
@CarroCompra
public class Carro implements Serializable {
    /**
     * Lista interna de items del carrito.
     *
     * <p>Observación: {@link #getItems()} devuelve la referencia a esta lista;
     * si desea proteger el estado interno, devuelva una copia (por ejemplo,
     * {@code Collections.unmodifiableList(new ArrayList<>(items))}).</p>
     */
    private List<ItemCarro> items;

    /**
     * Logger inyectado por CDI. Marcado como {@code transient} para evitar su serialización.
     */
    @Inject
    private transient Logger log;

    /**
     * Inicializa el carrito después de la construcción por el contenedor CDI.
     *
     * <p>Garantiza que {@code items} no sea {@code null} antes de usar los métodos públicos.</p>
     */
    @PostConstruct
    public void inicializar(){
        this.items = new ArrayList<>();
        log.info(" ----------- Inicializando el carro de compras");
    }

    /**
     * Lógica ejecutada antes de que el contenedor destruya la instancia.
     *
     * <p>Actualmente usado sólo para logging; puede ampliar limpieza si fuese necesario.</p>
     */
    @PreDestroy
    public void destruir(){
        log.info(" ----------- Destruyendo el carro de compras");
    }

    /**
     * Añade un {@code ItemCarro} al carrito.
     *
     * <p>Comportamiento:
     * - Si la lista ya contiene un item considerado "igual" según {@code equals}, incrementa su cantidad en 1.
     * - Si no existe, añade el {@code itemCarro} a la lista.</p>
     *
     * <p>Consideraciones:
     * - Depende de la implementación de {@code ItemCarro.equals(...)}, que debería identificar
     *   el mismo producto (no incluir la cantidad en la comparación) para que la lógica de
     *   incremento funcione correctamente.</p>
     *
     * <p>Posible mejora: evitar la doble búsqueda (primero {@code contains} y luego {@code stream().filter(...)})
     * y usar directamente {@code findAny()} para una sola pasada.</p>
     *
     * @param itemCarro el item a añadir o cuyo contador se incrementará
     */
    public void addItemCarro(ItemCarro itemCarro){
        if (items.contains(itemCarro)){
            // Buscar el mismo item existente y aumentar su cantidad en 1.
            // IMPORTANTE: esto sólo funciona correctamente si ItemCarro.equals compara la identidad del producto.
            Optional<ItemCarro> optionalItemCarro = items.stream()
                .filter(u -> u.equals(itemCarro))
                .findAny();
            if (optionalItemCarro.isPresent()){
                ItemCarro i = optionalItemCarro.get();
                i.setCantidad(i.getCantidad() + 1);
            }
        }else {
            this.items.add(itemCarro);
        }
    }

    /**
     * Devuelve la lista interna de {@code ItemCarro}.
     *
     * <p>Nota: devuelve la referencia interna. Si desea proteger la lista frente a modificaciones
     * desde el exterior, cambie la implementación para devolver una copia o una vista inmutable.</p>
     *
     * @return la lista de items del carrito (posiblemente vacía)
     */
    public List<ItemCarro> getItems() {
        return items;
    }

    /**
     * Calcula el total del carrito sumando {@code getImporte()} de cada item.
     *
     * <p>Devuelve un {@code int}. Si los precios usan decimales o se requiere alta precisión,
     * considere usar {@code BigDecimal} o representar las cantidades en la unidad mínima (por ejemplo, centavos).</p>
     *
     * @return la suma de importes de los items
     */
    public int getTotal(){
        return items.stream()
            .mapToInt(item -> item.getImporte())
            .sum();
    }

    /**
     * Elimina del carrito los items cuyo producto tiene el id proporcionado.
     *
     * <p>Asume que {@code i.getProducto()} y {@code getId()} no son {@code null}.</p>
     *
     * @param id id del producto a eliminar
     */
    public void deleteProducto(Long id){
        items.removeIf(i -> i.getProducto().getId().equals(id));
    }

    /**
     * Actualiza la cantidad de un item identificado por el id del producto.
     *
     * <p>Si se encuentra el item, se le asigna la nueva cantidad pasada en el argumento.
     * No valida la cantidad (por ejemplo, que sea positiva); sería recomendable validar
     * (p. ej. tratar 0 como eliminación o prohibir cantidades negativas).</p>
     *
     * @param id id del producto cuyo item se quiere actualizar
     * @param cantidad nueva cantidad a establecer
     */
    public void updateCarro(Long id, int cantidad){
        items.stream()
            .filter(i -> i.getProducto().getId().equals(id))
            .findAny()
            .ifPresent(i -> i.setCantidad(cantidad));
    }
}