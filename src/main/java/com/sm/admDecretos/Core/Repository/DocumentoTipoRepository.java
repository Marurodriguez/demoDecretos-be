package com.sm.admDecretos.Core.Repository;

import com.sm.admDecretos.Core.Entity.Db.Documento;
import com.sm.admDecretos.Core.Entity.Db.DocumentoTipo;
import com.sm.admDecretos.Core.Repository.Custom.DocumentoRepositoryCustom;


/**
 * Administra las interacciones con la base de datos de la entidad DocumentoTipo
 */
public interface DocumentoTipoRepository extends EntityRepository<DocumentoTipo> {

    // Obtiene un documentoTipo según el UUID
    DocumentoTipo findFirstByUuidEquals(String uuid);

    // Obtiene un documentoTipo según el ID
    DocumentoTipo findFirstByIdEquals(long id);

    // Obtiene un documentoTipo según el Nombre
    DocumentoTipo findFirstByNombreEqualsAndStatusEquals(String documentoTipoNombre, int status);
}
