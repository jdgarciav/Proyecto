package com.minsait.msvctienda.services;

import com.minsait.msvctienda.models.entity.Tienda;

import java.util.List;

public interface TiendaService {
    List<Tienda> findAll();
    Tienda findById(Long id);
    Tienda save(Tienda producto);
    boolean deleteById(Long id);
    //TODO: Realizar metodo en TiendaRepository para buscar por el nombre de la tienda
    //Tienda findByName(String nombre);
    //TODO: Realizar metodo en TiendaRepository para buscar por el cp
    //Tienda findByCP(String codigoPostal);
    //TODO: Realizar metodo en TiendaRepository para buscar por la ciudad
    //Tienda findByCity(String ciudad);
}
