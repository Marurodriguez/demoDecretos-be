package com.sm.admDecretos.Core.Entity.DTO;

import com.sm.admDecretos.Core.Entity.Db.Archivo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArchivoDTO {
    private Archivo archivo;
    private ArchivoDataDTO archivoDataDTO;

}
