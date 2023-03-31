package com.sm.admDecretos.Core.Repository;

import com.sm.admDecretos.Core.Entity.Db.ArchivoData;


/**
 * Administra las interacciones con la base de datos de la entidad ArchivoData
 * ArchivoData es la entidad que tiene los datos reales de los archivos relacionados al documento
 */
public interface ArchivoDataRepository extends EntityRepository<ArchivoData> {

    // Obtiene un documento según el UUID
    ArchivoData findFirstByUuidEquals(String uuid);

    // Obtiene un documento según el ID
    ArchivoData findFirstByIdEquals(long id);
}
