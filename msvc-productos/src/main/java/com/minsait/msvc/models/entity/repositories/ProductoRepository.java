package com.minsait.msvc.models.entity.repositories;

import com.minsait.msvc.models.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto,Long> {

}
