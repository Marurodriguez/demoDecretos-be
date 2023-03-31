package com.sm.admDecretos.Core.Entity.Db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


/**
 * Contiene la información real del archivo. Esta información puede estar encriptada o no dependiendo del tipo de
 * archivo.
 */
@Getter
@Setter
@Entity(name = "archivo_data")
public class ArchivoData extends AbstractEntity {
    @JsonIgnore
    @Column(columnDefinition = "LONGTEXT")
    private String data;

    @Column(name="filesize")
    private long fileSize;


    // Si encriptado = 0 ==> La información no está encriptada.
    // Si encriptado = 1 ==> La información está encriptada.
    @Column(name="encriptado")
    private int encriptado;
}
