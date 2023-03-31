package com.sm.admDecretos.Core.Repository;

import com.sm.admDecretos.Core.Entity.Db.Documento;
import com.sm.admDecretos.Core.Repository.Custom.DocumentoRepositoryCustom;

public interface DocumentoRepository extends EntityRepository<Documento>, DocumentoRepositoryCustom {

    // Obtiene un documento según el UUID
    Documento findFirstByUuidEquals(String uuid);

    // Obtiene un documento según el ID
    Documento findFirstByIdEquals(long id);

    Documento findFirstByPrefijoEqualsAndNumeroExpedienteEqualsAndAnioEqualsAndAlcanceEqualsAndStatusEquals(int prefijo, int numeroExpediente, int anio, int alcance, int status);
}
