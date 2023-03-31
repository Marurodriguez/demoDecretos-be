package com.sm.admDecretos.Core.Service;

import com.sm.admDecretos.Core.Entity.DTO.ArchivoDTO;
import com.sm.admDecretos.Core.Entity.DTO.ArchivoDataDTO;
import com.sm.admDecretos.Core.Entity.DTO.Mappers.ArchivoDTOMapper;
import com.sm.admDecretos.Core.Entity.Db.Archivo;
import com.sm.admDecretos.Core.Entity.Db.ArchivoData;
import com.sm.admDecretos.Core.Entity.Db.ArchivoTipo;
import com.sm.admDecretos.Core.Entity.Db.Documento;
import com.sm.admDecretos.Core.Entity.Virtual.PaginateList;
import com.sm.admDecretos.Core.Entity.Virtual.PaginateParameters;
import com.sm.admDecretos.Core.Repository.ArchivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArchivoService extends AbstractService<Archivo> {
    @Autowired
    ArchivoRepository archivoRepository;

    @Autowired
    ArchivoDTOMapper archivoDTOMapper;

    @Autowired
    ArchivoDataService archivoDataService;


    /**
     * Obtiene un paginate con los archivos (SIN LA INFORMACIÓN REAL)
     * @param paginateList
     * @return
     * @throws Exception
     */
    public Page<Archivo> paginate(PaginateList paginateList) throws Exception {
        return this.archivoRepository.paginate(paginateList);
    }

    /**
     * Obtiene el paginate con la información real de los archivos
     * @param paginateList
     * @return
     * @throws Exception
     */
    public Page<ArchivoDTO> paginateData(PaginateList paginateList) throws Exception {

        Page<Archivo> pageArchivo = this.archivoRepository.paginate(paginateList);
        // Pasar al DTO y obtener los archivos
        Page<ArchivoDTO> pageArchivoDto = this.archivoDTOMapper.convertToDto(pageArchivo);
        return pageArchivoDto;
    }


    /**
     * getArchivoDataByUuid
     * Retorna una entidad de ArchivoDTO con la información del dato que se necesita.
     * @param uuid unique universal id: Es el id único de la tabla archivo
     * @return
     * @throws Exception
     */
    public ArchivoDTO getArchivoDTOByUuid(String uuid) throws Exception {
        //Obtener el archivo
        Archivo archivo = this.getArchivoByUuid(uuid);
        if(archivo == null){
            throw new Exception("No existe el archivo con el uuid: " + uuid);
        }
        ArchivoDataDTO archivoDataDTO = this.archivoDataService.getArchivoDataDecoded(archivo.getArchivoData(),"");

        ArchivoDTO archivoDTO = new ArchivoDTO();
        archivoDTO.setArchivo(archivo);
        archivoDTO.setArchivoDataDTO(archivoDataDTO);
        return archivoDTO;
    }


    /**
     * getDocumentoByUuid
     * @param uuid unique universal id: Es el id único
     * @return Un unico documento que puede existir con el uuid
     * @throws Exception
     */
    public Archivo getArchivoByUuid(String uuid) throws Exception {
        return this.archivoRepository.findFirstByUuidEquals(uuid);
    }
    /**
     * getDocumentoById
     * @param id Id de la tabla documento
     * @return El documento con el id
     * @throws Exception
     */
    public Archivo getArchivoById(long id) throws Exception {
        return this.archivoRepository.findFirstByIdEquals(id);
    }

}
