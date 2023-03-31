package com.sm.admDecretos.Core.Entity.Db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


/**
 * Documento
 * Esta Entidad es la principal del proyecto, ya que guarda la información del documento digitalizado.
 * En esta entidad se guardará la información propia del expediente y la información asociada a la auditoría de quienes
 * y donde se digitalizo.
 * Por cada documento existen multiples archivos que se relacionan al documento: Por ejemplo imágenes, preview, ocr, pdf
 * la tabla de ARCHIVO apunta a esta, en una relación ManyToOne (Muchos archivos apuntan a un único documento)
 * Junto al tipo de archivo se pueden listar las imágenes o el PDF, etc.
 */
@Getter
@Setter
@Entity(name = "documento")
public class Documento extends AbstractEntity {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_documento_tipo")
    private DocumentoTipo documentoTipo;

    /**
     * Existen centros de digitalizacion, donde se digitaliza los expedientes, esto ayuda para saber
     * en que lugar se digitalizo el expediente.
     */
    @JsonIgnore //NO DEVOLVER EN CONSULTAS NORMALES ES PARA AUDITORIA INTERNA
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_centro_digitalizacion")
    private CentroDigitalizacion centroDigitalizacion;

    /**
     * Guarda información del empleado que hizo la digitalización
     */
    @JsonIgnore //NO DEVOLVER EN CONSULTAS NORMALES ES PARA AUDITORIA INTERNA
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_digitalizador")
    private Digitalizador digitalizador;

    /**
     * Es la dependencia que inicio el trámite. Puede ser una persona externa
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_dependencia_iniciadora")
    private Dependencia dependencia;

    @Column(name = "numero_expediente")
    private Integer numeroExpediente;
    @Column(name = "prefijo")
    private Integer prefijo;
    @Column(name = "anio")
    private Integer anio;
    @Column(name = "caratula")
    private String caratula;

    @Column(name = "alcance")
    private int alcance;

    /**
     * La matricula es un identificador antiguo que se usaba para identificar un expediente.
     * Por temas de compatibilidad lo seguiremos usando
     */
    @Column(name = "matricula")
    private int matricula;

    /**
     * Indica la cantidad de imagenes que tiene el documento relacionados (Sin contar los preview).
     */
    @Column(name = "cant_imagenes")
    private int cantImagenes;

    /**
     * Guarda el Id único de la maquina que digitalizo el documento. Sirve como auditoria
     */
    @JsonIgnore //NO DEVOLVER EN CONSULTAS NORMALES ES PARA AUDITORIA INTERNA
    @Column(name = "pc_id")
    private long pcId;



    @JsonIgnore //NO DEVOLVER EN CONSULTAS NORMALES ES PARA AUDITORIA INTERNA
    @Column(name = "fecha_digitalizacion")
    private Timestamp fechaDigitalizacion;



}
