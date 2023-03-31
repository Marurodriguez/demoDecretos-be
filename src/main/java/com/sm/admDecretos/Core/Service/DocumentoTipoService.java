package com.sm.admDecretos.Core.Service;

import com.sm.admDecretos.Core.Entity.Db.DocumentoTipo;
import com.sm.admDecretos.Core.Repository.DocumentoTipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentoTipoService {
    @Autowired
    DocumentoTipoRepository documentoTipoRepository;

    public List<DocumentoTipo> getAll() {
       return (List<DocumentoTipo>) documentoTipoRepository.findAll();
    }
}
