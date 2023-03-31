package com.sm.admDecretos.Core.Entity.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InformacionDTO {
    private Long id = 0L;
    private String uuid = "";
    private String codigo = "";
    private String nombre = "";
    private String valor = "";
}
