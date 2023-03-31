package com.sm.admDecretos.Core.Repository;

import com.sm.admDecretos.Core.Entity.Db.Digitalizador;
import com.sm.admDecretos.Core.Entity.Db.DocumentoTipo;


/**
 * Administra las interacciones con la base de datos de la entidad Digitalizador
 */
public interface DigitalizadorRepository extends EntityRepository<Digitalizador> {

    // Obtiene un documento según el UUID
    Digitalizador findFirstByUuidEquals(String uuid);

    // Obtiene un documento según el ID
    Digitalizador findFirstByIdEquals(long id);
}
