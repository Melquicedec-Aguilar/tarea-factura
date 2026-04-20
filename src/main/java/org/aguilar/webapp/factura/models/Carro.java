package org.aguilar.webapp.factura.models;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Inject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class Carro implements Serializable {
    private List<ItemCarro> items;

    @Inject
    private transient Logger log;

    @PostConstruct
    public void inicializar(){
        this.items = new ArrayList<>();
        log.info(" ----------- Inicializando el carro de compras");
    }

    @PreDestroy
    public void destruir(){
        log.info(" ----------- Destruyendo el carro de compras");
    }

    public void addItemCarro(ItemCarro itemCarro){
        if (items.contains(itemCarro)){
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

    public List<ItemCarro> getItems() {
        return items;
    }

    public int getTotal(){
        return items.stream()
            .mapToInt(item -> item.getImporte())
            .sum();
    }

    public void deleteProducto(Long id){
        items.removeIf(i -> i.getProducto().getId().equals(id));
    }

    public void updateCarro(Long id, int cantidad){
        items.stream()
            .filter(i -> i.getProducto().getId().equals(id))
            .findAny()
            .ifPresent(i -> i.setCantidad(cantidad));
    }
}
