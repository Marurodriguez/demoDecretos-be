package com.sm.admDecretos.Core.Repository;

import com.sm.admDecretos.Core.Entity.Db.Archivo;
import com.sm.admDecretos.Core.Repository.Custom.ArchivoRepositoryCustom;

public interface ArchivoRepository extends EntityRepository<Archivo>, ArchivoRepositoryCustom {

    // Obtiene un documento según el UUID
    Archivo findFirstByUuidEquals(String uuid);

    // Obtiene un documento según el ID
    Archivo findFirstByIdEquals(long id);
}
