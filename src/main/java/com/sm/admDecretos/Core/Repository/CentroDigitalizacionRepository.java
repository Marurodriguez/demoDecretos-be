package com.sm.admDecretos.Core.Repository;

import com.sm.admDecretos.Core.Entity.Db.CentroDigitalizacion;

public interface CentroDigitalizacionRepository extends EntityRepository<CentroDigitalizacion> {

    // Obtiene un documento según el UUID
    CentroDigitalizacion findFirstByUuidEquals(String uuid);

    // Obtiene un documento según el ID
    CentroDigitalizacion findFirstByIdEquals(long id);
}
