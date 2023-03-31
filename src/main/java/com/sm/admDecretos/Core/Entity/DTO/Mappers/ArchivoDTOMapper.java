package com.sm.admDecretos.Core.Entity.DTO.Mappers;

import com.sm.admDecretos.Core.Entity.DTO.ArchivoDTO;
import com.sm.admDecretos.Core.Entity.DTO.ArchivoDataDTO;
import com.sm.admDecretos.Core.Entity.Db.Archivo;
import com.sm.admDecretos.Core.Service.ArchivoDataService;
import com.sm.admDecretos.Core.Service.ArchivoService;
import com.sm.admDecretos.Core.Service.FunctionService;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class ArchivoDTOMapper {

    @Autowired
    ArchivoDataService archivoDataService;

    ModelMapper modelMapper;

    public ArchivoDTOMapper() {
        this.modelMapper = new ModelMapper();
        this.modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
    }


    public Page<ArchivoDTO> convertToDto(Page<Archivo> paginateArchivo) {
        return mapEntityPageIntoDtoPage(paginateArchivo);
    }
    private Page<ArchivoDTO> mapEntityPageIntoDtoPage(Page<Archivo> entities) {
        try {
            return entities.map(objectEntity -> this.convertArchivoDTO(objectEntity));
        } catch (Exception ex) {
            return null;
        }
    }

    private ArchivoDTO convertArchivoDTO(Archivo archivo) {
        ArchivoDTO archivoDTO = new ArchivoDTO();
        archivoDTO.setArchivo(archivo);

        ArchivoDataDTO archivoDataDTO = null;
        //Cargar los registros
        try {
            archivoDataDTO = this.archivoDataService.getArchivoDataDecoded(archivo.getArchivoData(), "");
        }catch(Exception ex){

        }

        archivoDTO.setArchivoDataDTO(archivoDataDTO);
        return archivoDTO;
    }

}
