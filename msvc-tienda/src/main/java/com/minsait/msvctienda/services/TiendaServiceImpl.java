package com.minsait.msvctienda.services;

import com.minsait.msvctienda.models.entity.Tienda;
import com.minsait.msvctienda.repositories.TiendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TiendaServiceImpl implements TiendaService{
    @Autowired
    private TiendaRepository tiendaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Tienda> findAll() {
        return tiendaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Tienda findById(Long id) {
        return tiendaRepository.findById(id).orElseThrow();
    }


    @Override
    @Transactional
    public Tienda save(Tienda tienda) {
        return tiendaRepository.save(tienda);
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        Optional<Tienda> tiendaOptional = tiendaRepository.findById(id);
        if (tiendaOptional.isPresent()){
            tiendaRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
