package com.sm.admDecretos.Core.Entity.Db;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;


/**
 * Contiene el tipo de documento o tipo de trámite. Indica de que tipo es el expediente, este tipo lo asigna
 * la municipalidad. Ejemplos: Alta de Ingresos Brutos, Habilitación de Comercio, etc. Nos permite conocer
 * la información que podría a llegar a contener el documento o expediente.
 */
@Getter
@Setter
@Entity(name = "documento_tipo")
public class DocumentoTipo extends AbstractEntity {
    @Column(name = "codigo")
    private String codigo;

    @Column(name = "nombre")
    private String nombre;
}
