package com.sm.admDecretos.Core.Controller;

import com.sm.admDecretos.Core.Entity.Db.Archivo;
import com.sm.admDecretos.Core.Entity.Db.Documento;
import com.sm.admDecretos.Core.Entity.Virtual.PaginateList;
import com.sm.admDecretos.Core.Entity.Virtual.PaginateParameters;
import com.sm.admDecretos.Core.Service.ArchivoDataService;
import com.sm.admDecretos.Core.Service.ArchivoService;
import com.sm.admDecretos.Core.Service.DocumentoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * ArchivoController se encarga de los endopoint relacionados a Archivo.
 * La entidad archivo es la que guarda la información real del archivo, tanto si es una imagen, como
 * un pdf. Los archivos pueden estar encriptados, por lo que hay que desencriptarlos cuando son enviados
 * al frontend. La seguridad de estos endpoint debe ser máxima.
 */
@RestController
@CrossOrigin
@RequestMapping(path = "/api/archivo")
public class ArchivoController extends AbstractController<Archivo> {
    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    ArchivoService archivoService;


    @GetMapping("/uuid/{uuid}")
    public Archivo getByUuid(@PathVariable("uuid") String uuid) throws Exception {
        return archivoService.getArchivoByUuid(uuid);
    }
    @GetMapping("/id/{id}")
    public Archivo getById(@PathVariable("id") long id) throws Exception {
        return archivoService.getArchivoById(id);
    }

    /**
     * Realiza un paginate de Archivos: En este caso se tiene que enviar el uuid del Documento
     *
     * @param paginateList El uuid del documento es un parametro obligatorio
     * @return
     * @throws Exception
     */
    @PostMapping("/paginate")
    public ResponseEntity<?> paginate(@RequestBody PaginateList paginateList) throws Exception {
        if(paginateList.getParameterByName("documentoUuid") == null){
            throw new Exception("El uuid del documento es un parametro obligatorio");
        }

        return new ResponseEntity(archivoService.paginate(paginateList), HttpStatus.OK);
    }

    @PostMapping("/paginate-preview")
    public ResponseEntity<?> paginatePreview(@RequestBody PaginateList paginateList) throws Exception {
        if(paginateList.getParameterByName("documentoUuid") == null){
            throw new Exception("El uuid del documento es un parametro obligatorio");
        }
        // Se agrega que es del tipo PREV
        paginateList.addParameter(new PaginateParameters("tipo", "prev"));
        return new ResponseEntity(archivoService.paginateData(paginateList), HttpStatus.OK);
    }

    /**
     * obtiene el archivo con el contenido del archivo, según el uuid de ARCHIVO
     * @param uuid
     * @return
     * @throws Exception
     */
    @GetMapping("/data/{uuid}")
    public ResponseEntity<?> getArchivoData(@PathVariable("uuid") String uuid) throws Exception {
        return new ResponseEntity(archivoService.getArchivoDTOByUuid(uuid), HttpStatus.OK);
    }

    /**
     * Dado el uuid del documento, devuelve un array de archivo tipo segun el código.
     * @param paginateList Es el paginateList.. Tiene que tener obligatoriamente el documentoUuid
     * EN TIPO TIENE QUE IR EL TIPO DEL DOCUMENTO (img,prev,ocr,pdf,*)
     * SI SE PONE * (ASTERISCO) DEVUELVE EL CONTENIDO DE TODOS LOS ARCHIVOS RELACIONADOS
     * @return
     * @throws Exception
     */
    @PostMapping("/paginate-tipo")
    public ResponseEntity<?> getArchivoByTipo(@RequestBody PaginateList paginateList)  throws Exception {
        if(paginateList.getParameterByName("documentoUuid") == null){
            throw new Exception("El uuid del documento es un parametro obligatorio");
        }

        // Se podría hacer que no sea obligatorio y hacer que no se filtre: Sin embargo, como está
        // funcion devuelve el contenido de cada archivo, es importante que el usuario que lo vaya a usar
        // sepa que se van a obtener todos los datos.
        if(paginateList.getParameterByName("tipo") == null){
            throw new Exception("El tipo del documento es un parametro obligatorio");
        }
        return new ResponseEntity(archivoService.paginateData(paginateList), HttpStatus.OK);
    }
}
