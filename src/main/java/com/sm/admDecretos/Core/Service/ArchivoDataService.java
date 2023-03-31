package com.sm.admDecretos.Core.Service;

import com.sm.admDecretos.Core.Entity.DTO.ArchivoDTO;
import com.sm.admDecretos.Core.Entity.DTO.ArchivoDataDTO;
import com.sm.admDecretos.Core.Entity.Db.Archivo;
import com.sm.admDecretos.Core.Entity.Db.ArchivoData;
import com.sm.admDecretos.Core.Entity.Virtual.PaginateList;
import com.sm.admDecretos.Core.Entity.Virtual.PaginateParameters;
import com.sm.admDecretos.Core.Repository.ArchivoDataRepository;
import com.sm.admDecretos.Core.Repository.ArchivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ArchivoDataService extends AbstractService<ArchivoData> {
    @Autowired
    ArchivoDataRepository archivoDataRepository;

    @Autowired
    EncodeService encodeService;


    /**
     * getArchivoDataByUuid
     * @param uuid unique universal id: Es el id único
     * @return Un unico archivoData que puede existir con el uuid
     * @throws Exception
     */
    public ArchivoData getArchivoDataByUuid(String uuid) throws Exception {
        return this.archivoDataRepository.findFirstByUuidEquals(uuid);
    }
    /**
     * getArchivoDataById
     * @param id Id de la tabla documento
     * @return El archivoData con el id
     * @throws Exception
     */
    public ArchivoData getArchivoDataById(long id) throws Exception {
        return this.archivoDataRepository.findFirstByIdEquals(id);
    }



    /**
     * Se encarga de devolver el archivo decodeado, según el uuid enviado
     * @param archivoDataUuid es el uuid del archivodata
     * @return Retorna el DTO con la información decodeada
     * @throws Exception
     */
    public ArchivoDataDTO getArchivoDataDecoded(long id, String archivoDataUuid) throws Exception {
        ArchivoData archivoData = null;
        if(id != 0){
            archivoData = this.getArchivoDataById(id);
        }else{
            archivoData = this.getArchivoDataByUuid(archivoDataUuid);
        }

        if(archivoData == null){
            throw new Exception("No existe la entidad ArchivoData con uuid: " + archivoDataUuid + " o id: " + id);
        }
        ArchivoDataDTO archivoDataDTO = new ArchivoDataDTO();
        archivoDataDTO.setUuid(archivoData.getUuid());
        archivoDataDTO.setFileSize(archivoData.getFileSize());
        if(archivoData.getEncriptado() == 0){ // No está encriptado.. devolver el mismo valor grabado
            archivoDataDTO.setData(archivoData.getData());
        }else{
            // Está encriptado hay que desencriptarlo
            String contentDecoded = this.encodeService.des(archivoData.getData());
            archivoDataDTO.setData(contentDecoded);
        }
        return archivoDataDTO;
    }
}
