package com.minsait.msvc.service;

import com.minsait.msvc.models.entity.Producto;

import java.math.BigDecimal;
import java.util.List;

public interface ProductoService {
    List<Producto> findAll();
    Producto findById(Long id);
    Producto save(Producto producto);
    boolean deleteById(Long id);
    BigDecimal checkPrice(Long id);
    //TODO: Realizar metodo en productoRepository para buscar por el nombre del producto
    //Producto findByName(String nombre);
}
