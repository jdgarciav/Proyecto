package com.minsait.msvc.controllers;

import com.minsait.msvc.models.entity.Producto;
import com.minsait.msvc.service.ProductoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/productos")
@Slf4j
public class ProductoController {
    @Autowired
    private ProductoService productoService;

    @GetMapping("/listar")
    @ResponseStatus(HttpStatus.OK)
    public List<Producto> findAll(){
        return productoService.findAll();
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<Producto> findById(@PathVariable Long id){
        try {
            Producto producto = productoService.findById(id);
            return ResponseEntity.ok(producto);
        }catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/listar/{id}/precio")
    public ResponseEntity<?> ckeckPrice(@PathVariable Long id){
        try {
            Producto producto = productoService.findById(id);
            return ResponseEntity.ok(producto.getPrecio());
        }catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public Producto guardar(@RequestBody Producto producto){
        return productoService.save(producto);
    }

    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        if (productoService.deleteById(id))
            return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Producto> update(@PathVariable Long id, @RequestBody Producto productoConCambios){
        try{
            Producto producto=productoService.findById(id);
            producto.setNombre(productoConCambios.getNombre());
            producto.setPrecio(productoConCambios.getPrecio());
            return new ResponseEntity<>(productoService.save(producto),HttpStatus.CREATED);
        }catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        }
    }


}
