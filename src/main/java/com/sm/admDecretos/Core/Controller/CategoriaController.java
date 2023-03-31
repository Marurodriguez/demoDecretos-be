package com.sm.admDecretos.Core.Controller;

import com.sm.admDecretos.Core.Entity.Virtual.PaginateList;
import com.sm.admDecretos.Core.Service.CategoriaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping(path = "/api/categoria")
public class CategoriaController {
    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    CategoriaService categoriaService;
/**
    @PostMapping("/paginate")
    public ResponseEntity<?> paginate(@RequestBody PaginateList paginateList) throws Exception {
        return new ResponseEntity(this.categoriaService.paginate(paginateList), HttpStatus.OK);
    }
    */
}
