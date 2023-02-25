package com.minsait.msvc.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.minsait.msvc.models.entity.Producto;
import com.minsait.msvc.service.ProductoService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(ProductoController.class)
class ProductoControllerTest {
    private String uri = "/api/v1/productos";
    ObjectMapper mapper;

    @BeforeEach
    void SetUp(){
        mapper=new ObjectMapper();
    }

    @Autowired
    private MockMvc mvc;
    @MockBean
    private ProductoService productoService;

    @Test
    void findAll() throws Exception {
        when(productoService.findAll()).thenReturn(List.of(
                Optional.of(new Producto(1L,"Xbox series S", 6499.00)).get(),
                Optional.of(new Producto(2L,"Play Station", 11499.00)).get()
        ));
        mvc.perform(get(uri + "/listar").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].nombre").value("Xbox series S"));

    }

    @Test
    void findById() throws Exception{
        String nombreEsperado="Xbox series S";
        double precioEsperado=6499.00;
        when(productoService.findById(1L)).thenReturn(Optional.of(new Producto(1L, nombreEsperado, precioEsperado)).get());
        mvc.perform(get(uri+"/listar/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nombre", Matchers.is(nombreEsperado)))
                .andExpect(jsonPath("$.precio").value(precioEsperado));
    }

    @Test
    void findByIdNotFound() throws Exception{
        when(productoService.findById(1L)).thenThrow(NoSuchElementException.class);
        mvc.perform(get(uri + "/listar/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void ckeckPrice() throws Exception {
        String nombreEsperado="Xbox series S";
        double precioEsperado=6499.00;
        when(productoService.checkPrice(1L)).thenReturn(Optional.of(new Producto(1L, nombreEsperado, precioEsperado)).get().getPrecio());
        mvc.perform(get(uri+"/listar/1/precio").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(precioEsperado));

    }

    @Test
    void ckeckPriceNotFound() throws Exception{
        when(productoService.checkPrice(1L)).thenThrow(NoSuchElementException.class);
        mvc.perform(get(uri + "/listar/1/precio").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void guardar() throws Exception {
        Long idEsperado = 1L;
        String nombreEsperado = "Xbox series S";
        double precioEsperado = 6499.00;
        Producto nuevoProducto=new Producto(null, nombreEsperado, precioEsperado);
        when(productoService.save(any(Producto.class))).then(invocation -> {
            Producto producto=invocation.getArgument(0);
            producto.setId(idEsperado);
            return producto;
        });
        mvc.perform(post(uri + "/crear").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(nuevoProducto)))
                .andExpectAll(
                        jsonPath("$.id",Matchers.is(idEsperado.intValue())),
                        jsonPath("$.nombre",Matchers.is(nombreEsperado)),
                        jsonPath("$.precio",Matchers.is(precioEsperado)),
                        status().isCreated()
                );
    }

    @Test
    void deleteFound() throws Exception {
        when(productoService.deleteById(1L)).thenReturn(true);
        mvc.perform(delete(uri+"/borrar/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

    }

    @Test
    void deleteNotFound() throws Exception {
        when(productoService.findById(2L)).thenReturn(new Producto(1L,"Play Station", 11499.00));
        mvc.perform(delete(uri+"/borrar/3").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    void updateFound() throws Exception {
        Long idEsperado = 1L;
        String nombreEsperado = "Play Station";
        double precioEsperado = 11499.00;
        Producto nuevoProducto = new Producto(null,nombreEsperado, precioEsperado);
        when(productoService.findById(1L)).thenReturn(new Producto(1L,"Xbox series S", 6499.00));
        when(productoService.save(any())).then(
                invocationOnMock->{
                    Producto producto = nuevoProducto;
                    producto.setId(idEsperado);
                    return producto;
                }
        );
        mvc.perform((put(uri+"/actualizar/1").contentType(MediaType.APPLICATION_JSON))
                        .content(mapper.writeValueAsString(nuevoProducto)))
                .andExpectAll(
                        jsonPath("$.id",Matchers.is(idEsperado.intValue())),
                        jsonPath("$.nombre",Matchers.is(nombreEsperado)),
                        jsonPath("$.precio",Matchers.is(precioEsperado)),
                        status().isCreated()
                );
    }

    @Test
    void updateNotFound() throws Exception {
        Producto nuevoProducto = new Producto(null,"Play Station", 11499.00);
        when(productoService.findById(3L)).thenThrow(NoSuchElementException.class);
        mvc.perform(put(uri+"/actualizar/3").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(nuevoProducto)))
                .andExpectAll(
                        status().isNotFound()
                );
    }
}