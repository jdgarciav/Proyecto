package com.minsait.msvc.service;

import com.minsait.msvc.models.entity.Producto;
import com.minsait.msvc.repositories.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@WebMvcTest(ProductoServiceImplTest.class)
class ProductoServiceImplTest {

    @Mock
    ProductoRepository productoRepository;

    @InjectMocks
    ProductoServiceImpl productoService;

    @Test
    void findAll() {
        Producto producto1 = new Producto(null, "Smart", 9000);
        Producto producto2 = new Producto(null, "Bicicleta" ,4000);
        when(productoRepository.findAll()).thenReturn(List.of(producto1,producto2));
        List<Producto> productos = productoService.findAll();
        assertEquals("Smart", productos.get(0).getNombre());
    }

    @Test
    void findById() {
        Long id = 1L;
        Producto producto = new Producto(id, "Smart", 9000);
        when(productoRepository.findById(id)).thenReturn(Optional.of(producto));
        Producto productoComparar = productoService.findById(id);
        assertEquals(id, productoComparar.getId());
        assertEquals("Smart", productoComparar.getNombre());
        assertEquals(9000, productoComparar.getPrecio());
    }

    @Test
    void checkPrice() {
        double precioEsperado = 9000;
        Producto producto = new Producto(1L, "Smart", precioEsperado);
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        double precio = productoService.checkPrice(1L);
        assertEquals(precioEsperado,precio);
    }

    @Test
    void save() {
        String nombre = "Smart";
        double precio = 9000;
        Producto producto = new Producto(null, nombre, precio);
        when(productoRepository.save(any(Producto.class))).then(
                invocation -> {
                    Producto productoRetorno = invocation.getArgument(0);
                    productoRetorno.setId(1L);
                    return productoRetorno;
                }
        );
        Producto productoComparar = productoService.save(producto);
        assertEquals(1L, productoComparar.getId());
        assertEquals(nombre, productoComparar.getNombre());
        assertEquals(precio,productoComparar.getPrecio());
    }

    @Test
    void deleteByIdFound() {
        Long id = 1L;
        Producto producto = new Producto(id, "Smart", 9000);
        doReturn(Optional.of(producto)).when(productoRepository).findById(id);
        assertTrue(productoService.deleteById(id));
    }

    @Test
    void deleteByIdNotFound() {
        Long id = 1L;
        Producto producto = new Producto(id, "Smart", 9000);
        doReturn(Optional.of(producto)).when(productoRepository).findById(id);
        assertFalse(productoService.deleteById(2L));
    }
}