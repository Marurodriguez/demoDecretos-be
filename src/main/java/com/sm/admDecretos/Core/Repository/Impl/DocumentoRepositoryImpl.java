package com.sm.admDecretos.Core.Repository.Impl;

import com.sm.admDecretos.Core.Entity.DTO.DocumentosPaginateFiltros;
import com.sm.admDecretos.Core.Entity.Db.Campo;
import com.sm.admDecretos.Core.Entity.Db.Documento;
import com.sm.admDecretos.Core.Entity.Virtual.PaginateList;
import com.sm.admDecretos.Core.Entity.Virtual.PaginateParameters;
import com.sm.admDecretos.Core.Repository.Custom.DocumentoRepositoryCustom;
import com.sm.admDecretos.Core.Repository.RepositoryImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DocumentoRepositoryImpl extends RepositoryImpl<Documento> implements DocumentoRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @PostConstruct
    void setEntity() {
        super.setEntityManager(entityManager);
    }

    /**
     * Se encarga de realizar la búsqueda de documentos según los filtros aplicados en el frontend
     *
     * @param paginateList Contiene los filtros
     * @return Un objeto del tipo Page
     */
    @Override
    @Transactional
    public Page<Documento> paginate(PaginateList paginateList) {
        super.init();
        super.setTableName("documento");
        //Solo los que tienen status = 0. Status=-1 se consideran borrados logicamente
        String sql = "t.status = 0 ";


        /*
            SETEA LOS FILTROS
         */

        // 1. Filtra por Tipo de Documento (que sería el tipo de trámite)
        PaginateParameters documentoTipoParameter = paginateList.getParameterByName("tipo");
        if (documentoTipoParameter != null) {
            sql += " and t.id_documento_tipo = :tipo ";
            super.addParameter("tipo", documentoTipoParameter.getValue());
        }
        // 2. Filtra por la dependencia Iniciadora del trámite
        PaginateParameters dependenciaParameter = paginateList.getParameterByName("dependencia");
        if (dependenciaParameter != null) {
            sql += " and t.id_dependencia_iniciadora = :dependencia ";
            super.addParameter("dependencia", dependenciaParameter.getValue());
        }


        // 3. Filtra por numero de expediente
        PaginateParameters expediente = paginateList.getParameterByName("expediente");
        if (expediente != null) {
            ArrayList<String> text = new ArrayList<String>();
            String t = expediente.getValue();

            if (t.contains("-")) {
                for (String t1 : t.split("-")) {
                    if (t1.contains("/")) {
                        Collections.addAll(text, t1.split("/"));
                    } else {
                        text.add(t1);
                    }
                }
            } else {
                if (t.contains("/")) {
                    Collections.addAll(text, t.split("/"));
                } else {
                    text.add(t);
                }
            }

            for (String tex : text)
                sql += " AND ( t.prefijo LIKE '%" + tex + "%' OR t.numero_expediente LIKE '%" + tex + "%' OR t.anio LIKE '%" + tex + "%' ) ";
        }


        // 4. Filtra por Caratula
        PaginateParameters caratulaParameter = paginateList.getParameterByName("caratula");
        if (caratulaParameter != null) {
            String[] text = caratulaParameter.getValue().split(" ");
            for (int i = 0; i < text.length; i++) {
                sql += " AND caratula LIKE '%" + text[i] + "%' ";
            }
        }

        // Define las condiciones y crea la paginación
        super.setWhereConditions(sql);
        Page<Documento> page = super.createSql(Documento.class, paginateList.getPage(), paginateList.getSize());
        return page;
    }

    Campo getCampoById(Long id, List<Campo> campos) {
        for (Campo c : campos) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }
}
