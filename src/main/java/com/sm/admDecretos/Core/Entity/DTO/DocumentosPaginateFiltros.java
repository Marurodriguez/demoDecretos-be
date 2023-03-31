package com.sm.admDecretos.Core.Entity.DTO;

import com.sm.admDecretos.Core.Entity.Db.Dependencia;
import com.sm.admDecretos.Core.Entity.Db.DocumentoTipo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DocumentosPaginateFiltros {
    private List<Dependencia> dependencias;
    private List<DocumentoTipo> documentoTipos;

    public DocumentosPaginateFiltros(List<Dependencia> dep, List<DocumentoTipo> docTipo) {
        this.dependencias = dep;
        this.documentoTipos = docTipo;
    }
}
