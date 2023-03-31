package com.sm.admDecretos.Core.Controller;

import com.sm.admDecretos.Core.Entity.Db.Decreto;
import com.sm.admDecretos.Core.Service.DecretoService;

import java.text.SimpleDateFormat;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("/api")
public class DecretoController {
    @Autowired
    private DecretoService decretoService;
    
    @GetMapping("/decretos")
    public List<Decreto> listar()
    {
        return decretoService.findAll();
    }
    
    @PostMapping("/decretos")
    public  Decreto guardar(@RequestBody Decreto decreto)
    {
        return decretoService.save(decreto);
    }
    
    @GetMapping("/decretos/{id}")
    public Decreto getUnDecreto(@PathVariable Integer id)
    {
        return decretoService.findById(id);
    }
    
    @PutMapping("/decretos/{id}")
    public Decreto modificar(@RequestBody Decreto decreto,@PathVariable Integer id)
    {
        Decreto decretoActual= decretoService.findById(id);
        decretoActual.setDecreto(decreto.getDecreto());
        decretoActual.setFecha(decreto.getFecha());
        decretoActual.setDenominacion(decreto.getDenominacion());
        decretoActual.setDetalle(decreto.getDetalle());

        
        return decretoService.save(decretoActual);
    }
    
    @DeleteMapping("/decretos/{id}")
    public void eliminar(@PathVariable Integer id)
    {
        decretoService.delete(id);
    }
    
}
