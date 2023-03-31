package com.sm.admDecretos.Core.Entity.Db;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;


/**
 * Dependencia
 * Las dependencias son las unidades o departametos internos o externos que existen y se asocian al expediente.
 * Un expediente se crear a partir de un trámite determinado, y se asocia a una dependencia iniciadora. Por ejemplo
 * el alta de ingresos brutos se crea con ese tipo de trámite, y se asocia a la persona que va a dar de alta y a la
 * dependencia tributaria de la municipalidad (que es la que participa en dar de alta el ingreso bruto).
 */
@Getter
@Setter
@Entity(name = "dependencia")
public class Dependencia extends AbstractEntity {
    @Column(name = "codigo")
    private String codigo;

    @Column(name = "nombre")
    private String nombre;
}
