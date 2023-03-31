package com.sm.admDecretos.Core.Controller;

import com.sm.admDecretos.Core.Entity.Db.Archivo;
import com.sm.admDecretos.Core.Service.ArchivoService;
import com.sm.admDecretos.Core.Service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * TestService
 * Sirve para realizar testeos
 */
@RestController
@CrossOrigin
@RequestMapping(path = "/api/test")
public class TestController extends AbstractController<Archivo> {
    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    TestService testService;

    @PostMapping("test")
    public Archivo test() throws Exception {
        testService.test();
        return null;
    }
    @PostMapping("test-decode")
    public Archivo testDecode() throws Exception {
        testService.testDecode();
        return null;
    }

    @PostMapping("test-upload")
    public Archivo testUpload() throws Exception {
        testService.testCreateDocumentos();
        return null;
    }

}
