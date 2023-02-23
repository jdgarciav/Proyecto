package com.minsait.msvc.service;

import com.minsait.msvc.models.entity.Producto;

import java.util.List;

public interface ProductoService {
    List<Producto> findAll();
    Producto findById(Long id);
    Producto save(Producto producto);
    void dalate(Long id);
}
