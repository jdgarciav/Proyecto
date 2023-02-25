package com.minsait.msvc.service;

import com.minsait.msvc.models.entity.Producto;
import com.minsait.msvc.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService{
    @Autowired
    private ProductoRepository productoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Producto findById(Long id) {
        return productoRepository.findById(id).orElseThrow();
    }

    @Override
    @Transactional(readOnly = true)
    public double checkPrice(Long id) {
        return productoRepository.findById(id).get().getPrecio();
    }

    @Override
    @Transactional
    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        Optional<Producto> productoOptional = productoRepository.findById(id);
        if (productoOptional.isPresent()){
            productoRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
