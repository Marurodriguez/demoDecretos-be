package com.sm.admDecretos.Core.Repository;

import com.sm.admDecretos.Core.Entity.Db.Dependencia;
import com.sm.admDecretos.Core.Entity.Db.Documento;
import com.sm.admDecretos.Core.Repository.Custom.DocumentoRepositoryCustom;

public interface DependenciaRepository extends EntityRepository<Dependencia> {

    // Obtiene un documento según el UUID
    Dependencia findFirstByUuidEquals(String uuid);

    // Obtiene un documento según el ID
    Dependencia findFirstByIdEquals(long id);

    // Obtiene un documento según el nombre. REGLA: NO PUEDE HABER DOS DEPENDENCIAS CON EL MISMO NOMBRE
    Dependencia findFirstByNombreEqualsAndStatusEquals(String dependenciaNombre, int status);
}
