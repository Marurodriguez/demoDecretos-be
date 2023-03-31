package com.sm.admDecretos.Core.Entity.Db;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Digitalizador
 * Guarda la información de la persona que realizo la digitalización del documento y que es el responsable
 * ante una eventualidad de lo cargado.
 */
@Getter
@Setter
@Entity(name = "digitalizador")
public class Digitalizador extends AbstractEntity {
    @Column(name = "username")
    private String username;

    @Column(name = "nombre")
    private String nombre;
}
