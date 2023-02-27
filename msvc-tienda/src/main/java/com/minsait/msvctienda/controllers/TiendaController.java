package com.minsait.msvctienda.controllers;

import com.minsait.msvctienda.models.entity.Tienda;
import com.minsait.msvctienda.repositories.TiendaRepository;
import com.minsait.msvctienda.services.TiendaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/productos")
@Slf4j
public class TiendaController {
    @Autowired
    private TiendaService tiendaService;

    @GetMapping("/listar")
    @ResponseStatus(HttpStatus.OK)
    public List<Tienda> findAll(){
        return tiendaService.findAll();
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<Tienda> findById(@PathVariable Long id){
        try {
            Tienda tienda = tiendaService.findById(id);
            return ResponseEntity.ok(tienda);
        }catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/listar/{id}/direccion")
    public ResponseEntity<?> checkDirection(@PathVariable Long id){
        try {
            Tienda tienda = tiendaService.findById(id);
            return ResponseEntity.ok("Nombre de la tienda: " + tienda.getNombre()
                    + "\nDireccion: " + tienda.getCiudad() + " " + tienda.getCodigoPostal() );
        }catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public Tienda guardar(@RequestBody Tienda tienda){
        return tiendaService.save(tienda);
    }

    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        if (tiendaService.deleteById(id))
            return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Tienda> update(@PathVariable Long id, @RequestBody Tienda tiendaConCambios){
        try{
            Tienda tienda= tiendaService.findById(id);
            tienda.setNombre(tiendaConCambios.getNombre());
            tienda.setCiudad(tiendaConCambios.getCiudad());
            tienda.setCodigoPostal(tiendaConCambios.getCodigoPostal());
            return new ResponseEntity<>(tiendaService.save(tienda),HttpStatus.CREATED);
        }catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        }
    }


}
