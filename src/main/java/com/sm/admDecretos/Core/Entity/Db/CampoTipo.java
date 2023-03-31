package com.sm.admDecretos.Core.Entity.Db;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter
@Entity(name = "campo_tipo")
public class CampoTipo extends AbstractEntity {
    @Column(name = "codigo")
    private String codigo;
    @Column(name = "nombre")
    private String nombre;
}
