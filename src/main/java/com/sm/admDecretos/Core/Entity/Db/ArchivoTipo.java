package com.sm.admDecretos.Core.Entity.Db;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * ArchivoTIPO
 * Indica de que tipo es el archivo que está relacionado al documento: Los tipos que se conocen actualmente son:
 * Las imágenes, los preview, el archivo OCR y el archivo PDF. Es importante filtrar por este tipo para poder
 * obtener los archivos requeridos en la acción correspondiente.
 */
@Getter
@Setter
@Entity(name = "archivo_tipo")
public class ArchivoTipo extends AbstractEntity {
    @Column(name = "codigo")
    private String codigo;

    @Column(name = "nombre")
    private String nombre;
}
