package com.minsait.msvctienda.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tiendas")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Tienda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nombre;

    @Column(unique = true)
    private String ciudad;

    @Column(unique = true)
    private String codigoPostal;


}
