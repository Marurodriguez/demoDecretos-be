package com.sm.admDecretos.Core.Entity.Db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


/**
 * Archivo
 * Es la tabla que contiene la información del archivo relacionado a Documento. Un documento puede tener varios
 * archivos: (Imagenes, OCR, PDF, etc..) por cada uno se graba un registro en esta tabla, apuntando a la tabla
 * documento correspondiente, y definiendo el TIPO. En el caso de que sea una imagen, habrá un enlace a la
 * tabla archivo_data donde estará el archivo preview.
 */
@Getter
@Setter
@Entity(name = "archivo")
public class Archivo extends AbstractEntity {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_archivo_tipo")
    private ArchivoTipo archivoTipo;


    /**
    * No se crea la entidad ya que no queremos que hibernate use joins
    * Apunta a la tabla donde contiene el archivo
    */

    @JsonIgnore // No es bueno que se tenga la información del ID del documento
    @Column(name = "id_archivo_data")
    private long archivoData;


    /**
     * No se crea la entidad ya que no queremos que hibernate use joins
     * Apunta a la tabla donde contiene la imagen preview (en el caso de que exista)
     */

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_documento")
    private Documento documento;

    @Column(name="extension")
    private String extension;

    @JsonIgnore
    @Column(name ="hash", columnDefinition = "LONGTEXT")
    private String hash;

    @Column(name = "orden")
    private int orden;

    @Column(name = "uuid_alternativo",columnDefinition = "CHAR(36)")
    private String uuidAlternativo;
}
