package com.sm.admDecretos.Core.Service;

import com.sm.admDecretos.Core.Entity.DTO.DocumentosPaginateFiltros;
import com.sm.admDecretos.Core.Entity.Db.Dependencia;
import com.sm.admDecretos.Core.Entity.Db.Documento;
import com.sm.admDecretos.Core.Entity.Db.DocumentoTipo;
import com.sm.admDecretos.Core.Entity.Virtual.PaginateList;
import com.sm.admDecretos.Core.Repository.DocumentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Permite administrar las acciones en un documento
 */
@Service
public class DocumentoService extends AbstractService<Documento> {
    @Autowired
    DocumentoRepository documentoRepository;
    @Autowired
    DocumentoTipoService documentoTipoService;
    @Autowired
    DependenciaService dependenciaService;


    public Page<Documento> paginate(PaginateList paginateList) throws Exception {
        return this.documentoRepository.paginate(paginateList);
    }

    /**
     * getDocumentoByUuid
     * @param uuid unique universal id: Es el id único
     * @return Un unico documento que puede existir con el uuid
     * @throws Exception
     */
    public Documento getDocumentoByUuid(String uuid) throws Exception {
        return this.documentoRepository.findFirstByUuidEquals(uuid);
    }
    /**
     * getDocumentoById
     * @param id Id de la tabla documento
     * @return El documento con el id
     * @throws Exception
     */
    public Documento getDocumentoById(long id) throws Exception {
        return this.documentoRepository.findFirstByIdEquals(id);
    }


    /**
     * paginateFiltros
     * @return listado de dependencias y tipos de documento, para que el front pueda hacer filtros en el paginado
     */
    public DocumentosPaginateFiltros paginateFiltros() {
        return new DocumentosPaginateFiltros(dependenciaService.getAll(), documentoTipoService.getAll());
    }

    /**
    public boolean addEjemplos() throws Exception {
        return this.documentoRepository.addEjemplos(2);
    }

    public boolean addArchivosEjemplo(Long id, Integer cantidad) throws Exception {
        return this.documentoRepository.addArchivosEjemplo(id, cantidad);
    }


    public Archivo getArchivoByUuid(String uuid) throws Exception {
        return this.documentoRepository.getArchivoByUuid(uuid);
    }


    public DocumentoDetalles informacion(String uuid) {
        Documento documento = this.documentoRepository.findByUuidEquals(uuid);
        DocumentoDetalles documentoDetalles = new DocumentoDetalles();
        documentoDetalles.setUuid(documento.getUuid());
        documentoDetalles.setPrefijo(documento.getPrefijo());
        documentoDetalles.setNumero_expediente(documento.getNumero_expediente());
        documentoDetalles.setAnio(documento.getAnio());
        documentoDetalles.setCaratula(documento.getCaratula());
        documentoDetalles.setDependencia_iniciadora(documento.getDependencia_iniciadora());
        documentoDetalles.setCategoria(documento.getCategoria());
        documentoDetalles.setInformacion(documento.getInformacion());
        return documentoDetalles;
    }

    public String ocrTexto(String uuid) throws Exception {
        Documento documento = this.documentoRepository.findByUuidEquals(uuid);
        return documento.des(documento.getOcr_texto_encr());
    }

    public Documento archivo(String uuid) throws Exception {
        Documento documento = this.documentoRepository.findByUuidEquals(uuid);
        documento.desencriptar();
        return documento;
    }

    public ArchivoDensencriptadoDTO archivoPdf(String uuid) throws Exception {
        Archivo archivo = this.documentoRepository.archivoPdf(uuid);
        archivo.desencriptar_archivo();
        ArchivoDensencriptadoDTO archivoDto = new ArchivoDensencriptadoDTO();
        archivoDto.setUuid(archivo.getUuid());
        archivoDto.setId_archivo_tipo(archivo.getId_archivo_tipo());
        archivoDto.setExtension(archivo.getExtension());
        archivoDto.setPeso_kb(archivo.getPeso_kb());
        archivoDto.setArchivo_desc(archivo.getArchivo_desc());
        return archivoDto;
    }

    public List<Archivo> getArchivos(Long documentId) {
        System.out.println("Test getarchivos");
        String archivoSql = "SELECT * FROM archivos WHERE id_documento=" + documentId;
        System.out.println(archivoSql);
        Query archivoQuery = entityManager.createNativeQuery(archivoSql, Archivo.class);
        List<Archivo> archivoList = archivoQuery.getResultList();
        return archivoList;
        //return this.documentoRepository.getArchivosByDocumentId(documentId);
    }
     */
}
