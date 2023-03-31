package com.sm.admDecretos.Core.Service;

import com.sm.admDecretos.Core.Entity.Db.AbstractEntity;
import com.sm.admDecretos.Core.Repository.EntityRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class AbstractService<T extends AbstractEntity> {

    private EntityRepository entityRepository;

    protected void setEntityRepository(EntityRepository abstractEntity) {
        this.entityRepository = abstractEntity;
    }


    /**
     * Retorna un objeto según el ID
     *
     * @param id
     * @return Objeto o null si hay error o no existe
     */
    public T findOneById(Long id) {
        try {
            return (T) this.entityRepository.findById(id).orElse(null);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Retorna un objeto según el hash Único
     * REGLA: SI EL UUID ES VACIO O NULL RETORNA NULL
     *
     * @param uuid
     * @return Objeto o null si hay error o no existe
     */
    /*public T findOneByUuid(String uuid) {
        try {
            if (uuid.isEmpty() || uuid.equalsIgnoreCase("") || uuid == null) {
                return null;
            }
            T entity = (T) this.entityRepository.findByUuidEqualsAndStatusEquals(uuid, 0);
            return entity;
        } catch (Exception ex) {
            return null;
        }
    }
*/

    /**
     * Genera un hash único para el objeto
     *
     * @param uniqueString cadena de valores unico
     * @return
     */
    public String getUuid(String uniqueString) throws Exception {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
