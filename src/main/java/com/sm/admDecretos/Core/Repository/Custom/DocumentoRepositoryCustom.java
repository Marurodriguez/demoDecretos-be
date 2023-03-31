package com.sm.admDecretos.Core.Repository.Custom;

import com.sm.admDecretos.Core.Entity.DTO.DocumentosPaginateFiltros;
import com.sm.admDecretos.Core.Entity.Db.Archivo;
import com.sm.admDecretos.Core.Entity.Db.Documento;
import com.sm.admDecretos.Core.Entity.Virtual.PaginateList;
import org.springframework.data.domain.Page;

public interface DocumentoRepositoryCustom {
    Page<Documento> paginate(PaginateList paginateList);

    //boolean addEjemplos(Integer cantidad) throws Exception;

    //boolean addArchivosEjemplo(Long id, Integer cantidad) throws Exception;

    //Archivo archivoPdf(String uuid);

    //Archivo getArchivoByUuid(String uuid) throws Exception;

    //String getArchivosByDocumentId(Long documentId);

}
