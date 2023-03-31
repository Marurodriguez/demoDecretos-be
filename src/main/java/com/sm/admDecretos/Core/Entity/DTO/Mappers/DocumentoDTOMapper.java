package com.sm.admDecretos.Core.Entity.DTO.Mappers;

import com.sm.admDecretos.Core.Service.FunctionService;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DocumentoDTOMapper {

    //@Autowired
    //DocumentoRegistroService documentoRegistroService;

    @Autowired
    FunctionService functionService;

    ModelMapper modelMapper;

    public DocumentoDTOMapper() {
        this.modelMapper = new ModelMapper();
        this.modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
    }

/*
    public Page<DocumentoDTO> convertToDto(Page<Documento> paginateDocumento) {
        return mapEntityPageIntoDtoPage(paginateDocumento);
    }

    private DocumentoDTO convertDocumentoDTO(Documento documento) {
        DocumentoDTO documentoDTO = new DocumentoDTO();
        documentoDTO.setCredito(documento.getCredito());
        documentoDTO.setDebito(documento.getDebito());
        documentoDTO.setDescripcion(documento.getDescripcion());
        documentoDTO.setId(documento.getId());
        documentoDTO.setIdEmpresa(documento.getEmpresa().getId());
        documentoDTO.setIdNegocio(documento.getNegocio().getId());
        documentoDTO.setReferencia(documento.getReferencia());
        documentoDTO.setFecha(functionService.getStringFromTimestamp(documento.getFecha(), "dd/MM/yyyy"));

        //Cargar los registros
        try {
            documentoDTO.setRegistros(this.documentoRegistroService.getListDTO(documento));
        } catch (Exception ex) {
            // No hacer nada!
        }

        return documentoDTO;
    }


    private Page<DocumentoDTO> mapEntityPageIntoDtoPage(Page<Documento> entities) {
        try {
            return entities.map(objectEntity -> this.convertDocumentoDTO(objectEntity));
        } catch (Exception ex) {
            return null;
        }
    }
     */
}
