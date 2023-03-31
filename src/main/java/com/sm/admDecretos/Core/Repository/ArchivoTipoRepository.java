package com.sm.admDecretos.Core.Repository;

import com.sm.admDecretos.Core.Entity.Db.ArchivoTipo;
import com.sm.admDecretos.Core.Entity.Db.DocumentoTipo;


/**
 * Administra las interacciones con la base de datos de la entidad Archivo_tipo
 */
public interface ArchivoTipoRepository extends EntityRepository<ArchivoTipo> {

    // Obtiene un documento según el UUID
    ArchivoTipo findFirstByUuidEquals(String uuid);

    // Obtiene un documento según el ID
    ArchivoTipo findFirstByIdEquals(long id);

    ArchivoTipo findFirstByCodigoEqualsAndStatusEquals(String codigo, int status);
}
