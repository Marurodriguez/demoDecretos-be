package com.sm.admDecretos.Core.Service;

import com.sm.admDecretos.Core.Entity.Db.AbstractEntity;
import com.sm.admDecretos.Core.Entity.Db.ArchivoTipo;
import com.sm.admDecretos.Core.Entity.Db.Dependencia;
import com.sm.admDecretos.Core.Repository.DependenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Permite administrar las acciones en un dependencia
 */
@Service
public class DependenciaService extends AbstractService<AbstractEntity> {


    @Autowired
    DependenciaRepository dependenciaRepository;

    /**
     * getDependenciaByUuid
     * @param uuid unique universal id: Es el id único
     * @return Una única entidad
     * @throws Exception
     */
    public Dependencia getDependenciaByUuid(String uuid) {
        try {
            return this.dependenciaRepository.findFirstByUuidEquals(uuid);
        }catch (Exception ex){
            return null;
        }
    }

    /**
     * getDependenciaById
     * @param id Id de la tabla
     * @return El getArchivoTipoById con el id
     * @throws Exception
     */
    public Dependencia getDependenciaById(long id) {
        try{
            return this.dependenciaRepository.findFirstByIdEquals(id);
        }catch (Exception ex){
            return null;
        }
    }

    /**
     * getDependenciaById
     * @param dependenciaNombre indica el nombre de la dependencia (no puede haber dos nombres iguales)
     * @return El getArchivoTipoById con el id
     * @throws Exception
     */
    public Dependencia getDependenciaByNombre(String dependenciaNombre) {
        try{
            return this.dependenciaRepository.findFirstByNombreEqualsAndStatusEquals(dependenciaNombre,0);
        }catch (Exception ex){
            return null;
        }
    }

    public List<Dependencia> getAll() {
        return (List<Dependencia>) this.dependenciaRepository.findAll();
    }
}



