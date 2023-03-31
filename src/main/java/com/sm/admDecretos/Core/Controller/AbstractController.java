package com.sm.admDecretos.Core.Controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sm.admDecretos.Core.Entity.Db.AbstractEntity;
import com.sm.admDecretos.Core.Service.AbstractService;

public class AbstractController< T extends AbstractEntity> {
    private AbstractService abstractService;

    public void setService(AbstractService abstractService){
        this.abstractService = abstractService;
    }

    /**
    @GetMapping("/uuid/{uuid}")
    public ResponseEntity<?> getControllerEntity (@PathVariable("uuid") String uuid) {
        return new ResponseEntity(abstractService.findOneByUuid(uuid), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getControllerEntity (@PathVariable("id") Long id) {
        return new ResponseEntity(abstractService.findOneById(id), HttpStatus.OK);
    }
*/
//
//    @PostMapping("")
//    public ResponseEntity<?> addControllerEntity (@RequestBody T abstractEntity) {
//        System.out.println("addControllerEntity");
//        return new ResponseEntity(abstractService.addEntity(abstractEntity), HttpStatus.CREATED);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<?> editControllerEntity (@PathVariable("id") Long id, @RequestBody T abstractEntity) {
//        return new ResponseEntity(abstractService.editEntity(id, abstractEntity), HttpStatus.OK);
//
//    }
//
//    @CrossOrigin
//    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<?> pathControllerEntity (@RequestBody Map<String, Object> updates, @PathVariable("id") Long id) {
//        return new ResponseEntity(abstractService.pathEntity(updates, id), HttpStatus.OK);
//    }


//    @DeleteMapping("/ldelete/{id}")
//    public ResponseEntity<?> LogicalDeleteControllerEntity (@PathVariable("id") Long id) {
//        return abstractService.LogicalDeleteEntity(id);
//    }

//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteControllerEntity (@PathVariable("id") Long id) {
//        return abstractService.deleteEntity(id);
//    }

//    @GetMapping("/all")
//    public ResponseEntity<?> getAllControllerEntity() {
//        return new ResponseEntity(abstractService.findAll(), HttpStatus.OK);
//    }
//
//    //   /paginate?page=1&size=1&sort=nombre,asc
//    @RequestMapping(value="/paginate", method= RequestMethod.GET)
//    public ResponseEntity<Page<Poll>> getPaginate(@PageableDefault(value=50, page=0) Pageable pageable) {
//        return new ResponseEntity(abstractService.paginate(pageable), HttpStatus.OK);
//    }

//    @PostMapping("/list")
//    public ResponseEntity<?> getSearchControllerEntity (String search, Pageable page){
//        return new ResponseEntity(abstractService.search(search, page), HttpStatus.OK);
//    }

}