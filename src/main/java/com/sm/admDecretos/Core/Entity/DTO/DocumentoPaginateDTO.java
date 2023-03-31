package com.sm.admDecretos.Core.Entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DocumentoPaginateDTO {
    private String uuid = "";
    private Integer prefijo;
    private Integer numero_expediente = 0;
    private Integer anio = 0;
    private Integer caratula = 0;
    private String dependencia_iniciadora = "";
    //private Categoria categoria = new Categoria();

}
