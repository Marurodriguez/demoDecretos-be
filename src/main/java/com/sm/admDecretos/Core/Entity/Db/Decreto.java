package com.sm.admDecretos.Core.Entity.Db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name="Tb_decretos")
public class Decreto {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="Id")
    private Integer Id;

    @Column(name="Decreto")
    private String Decreto;

    @Column(name="Fecha")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date Fecha;

    @Column(name="Denominacion")
    private String Denominacion;

    @Column(name="Detalle")
    private String Detalle;
    
}
