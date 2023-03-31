package com.sm.admDecretos.Core.Entity.Db;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Indica el Centro de Digitalización en el que se digitaliza el documento. El centro está asociado al documento y
 * permite saber de que centro provino la digitalización. Es útil para realizar auditorias de los datos, ante una carga
 * erronea poder saber de donde provino, que maquina, que usuario y en que hora se digitalizó el documento.
 */
@Getter
@Setter
@Entity(name = "centro_digitalizacion")
public class CentroDigitalizacion extends AbstractEntity {
    @Column(name = "codigo")
    private String codigo;

    @Column(name = "nombre")
    private String nombre;
}
