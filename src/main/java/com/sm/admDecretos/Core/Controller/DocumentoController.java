package com.sm.admDecretos.Core.Controller;

import com.sm.admDecretos.Core.Entity.DTO.DocumentosPaginateFiltros;
import com.sm.admDecretos.Core.Entity.Db.Documento;
import com.sm.admDecretos.Core.Entity.Virtual.PaginateList;
import com.sm.admDecretos.Core.Service.DocumentoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Se encarga de la administración de los puntos de acceso relacionados a DOCUMENTOS.
 * El documento es la entidad principal, refleja la información del expediente cargado y por cada
 * documento puede haber varios archivos relacionados.
 * Los accesos a documento deben estar registringidas, el usuario que solicita el documento para
 * visualizar la información debe tener los permisos.
 * Asímismo se debe permitir la paginación con los filtros y la búsqueda.
 */
@RestController
@CrossOrigin
@RequestMapping(path = "/api/documento")
public class DocumentoController extends AbstractController<Documento> {
    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    DocumentoService documentoService;


    @GetMapping("/uuid/{uuid}")
    public Documento getDocumentoByUuid(@PathVariable("uuid") String uuid) throws Exception {
        return documentoService.getDocumentoByUuid(uuid);
    }
    @GetMapping("/id/{id}")
    public Documento getDocumentoByUuid(@PathVariable("id") long id) throws Exception {
        return documentoService.getDocumentoById(id);
    }
    @PostMapping("/paginate")
    public ResponseEntity<?> paginate(@RequestBody PaginateList paginateList) throws Exception {
        Page<Documento> documentos = this.documentoService.paginate(paginateList);
        return new ResponseEntity(documentos, HttpStatus.OK);
    }
    @GetMapping("/paginate/filtros")
    public ResponseEntity<?> paginateFiltros() throws Exception {
        DocumentosPaginateFiltros paginateFiltros = this.documentoService.paginateFiltros();
        return new ResponseEntity(paginateFiltros, HttpStatus.OK);
    }

/**

    @GetMapping("/add/ejemplos")
    public ResponseEntity<?> detalles() throws Exception {
        boolean ejemplos = this.documentoService.addEjemplos();
        return new ResponseEntity(ejemplos, HttpStatus.OK);
    }

    @GetMapping("/add/ejemplos/archivos/{id}/{cantidad}")
    public ResponseEntity<?> addArchivosEjemplo(@PathVariable("id") Long id, @PathVariable("cantidad") Integer cantidad) throws Exception {
        boolean ejemplos = this.documentoService.addArchivosEjemplo(id, cantidad);
        return new ResponseEntity(ejemplos, HttpStatus.OK);
    }

    @GetMapping("/detalles/{uuid}")
    public ResponseEntity<?> detalles(@PathVariable("uuid") String uuid) throws Exception {
        DocumentoDetalles documento = this.documentoService.informacion(uuid);
        return new ResponseEntity(documento, HttpStatus.OK);
    }

    @GetMapping("/detalles/ocrtexto/{uuid}")
    public ResponseEntity<?> ocrtextoOcrtexto(@PathVariable("uuid") String uuid) throws Exception {
        String documento = this.documentoService.ocrTexto(uuid);
        return new ResponseEntity(documento, HttpStatus.OK);
    }

    @PostMapping("/detalles/archivos")
    public ResponseEntity<?> archivosPaginate(@RequestBody PaginateList paginateList) throws Exception {
        Page<Archivo> archivos = this.archivoService.paginate(paginateList);
        return new ResponseEntity(archivos, HttpStatus.OK);
    }

    @GetMapping("/detalles/archivos/{uuid}")
    public ResponseEntity<?> getArchivoByUuid(@PathVariable("uuid") String uuid) throws Exception {
        Archivo archivo = this.documentoService.getArchivoByUuid(uuid);
        return new ResponseEntity(archivo, HttpStatus.OK);
    }

    @GetMapping("/archivo/{uuid}")
    public ResponseEntity<?> archivo(@PathVariable("uuid") String uuid) throws Exception {
        Documento documento = this.documentoService.archivo(uuid);
        return new ResponseEntity(documento, HttpStatus.OK);
    }

    @GetMapping("/pdf/{documento_uuid}")
    public ResponseEntity<?> archivoPdf(@PathVariable("documento_uuid") String documento_uuid) throws Exception {
        ArchivoDensencriptadoDTO archivo = this.documentoService.archivoPdf(documento_uuid);
        return new ResponseEntity(archivo, HttpStatus.OK);
    }

    */
}
