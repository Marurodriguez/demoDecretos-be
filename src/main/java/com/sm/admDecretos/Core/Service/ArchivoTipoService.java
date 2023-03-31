package com.sm.admDecretos.Core.Service;

import com.sm.admDecretos.Core.Entity.DTO.ArchivoDataDTO;
import com.sm.admDecretos.Core.Entity.Db.ArchivoData;
import com.sm.admDecretos.Core.Entity.Db.ArchivoTipo;
import com.sm.admDecretos.Core.Repository.ArchivoDataRepository;
import com.sm.admDecretos.Core.Repository.ArchivoTipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArchivoTipoService extends AbstractService<ArchivoData> {
    @Autowired
    ArchivoTipoRepository archivoTipoRepository;

    /**
     * getArchivoTipo
     * @param uuid unique universal id: Es el id único
     * @return Un unico ArchivoTipo que puede existir con el uuid
     * @throws Exception
     */
    public ArchivoTipo getArchivoTipoByUuid(String uuid) {
        try {
            return this.archivoTipoRepository.findFirstByUuidEquals(uuid);
        }catch (Exception ex){
            return null;
        }

    }
    /**
     * getArchivoTipoById
     * @param id Id de la tabla
     * @return El getArchivoTipoById con el id
     * @throws Exception
     */
    public ArchivoTipo getArchivoTipoById(long id) {
        try{
            return this.archivoTipoRepository.findFirstByIdEquals(id);
        }catch (Exception ex){
            return null;
        }

    }
    /**
     * getArchivoTipoByCodigo
     * @param codigo de la tabla.En la tabla está en minusculas
     * @return El objeto con el valor
     * @throws Exception
     */
    public ArchivoTipo getArchivoTipoByCodigo(String codigo) {
        try{
            return this.archivoTipoRepository.findFirstByCodigoEqualsAndStatusEquals(codigo,0);
        }catch (Exception ex){
            return null;
        }

    }
}
