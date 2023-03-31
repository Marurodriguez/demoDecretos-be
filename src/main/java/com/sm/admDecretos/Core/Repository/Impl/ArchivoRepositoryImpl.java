package com.sm.admDecretos.Core.Repository.Impl;

import com.sm.admDecretos.Core.Entity.Db.Archivo;
import com.sm.admDecretos.Core.Entity.Db.Documento;
import com.sm.admDecretos.Core.Entity.Virtual.PaginateList;
import com.sm.admDecretos.Core.Entity.Virtual.PaginateParameters;
import com.sm.admDecretos.Core.Repository.Custom.ArchivoRepositoryCustom;
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
import java.util.List;

public class ArchivoRepositoryImpl extends RepositoryImpl<Archivo> implements ArchivoRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @PostConstruct
    void setEntity() {
        super.setEntityManager(entityManager);
    }

    @Override
    @Transactional
    public Page<Archivo> paginate(PaginateList paginateList) {
        super.init();
        super.setTableName("archivo");
        super.setQueryJString(" inner join documento d on d.id = t.id_documento " +
                              " inner join archivo_tipo tipo on tipo.id = t.id_archivo_tipo ");
        //Solo los que tienen status = 0. Status=-1 se consideran borrados logicamente
        String sql = "t.status = 0 ";
        /*
            SETEA LOS FILTROS
         */

        // 1. Filtra por documento
        PaginateParameters documentoParameter = paginateList.getParameterByName("documentoUuid");
        if (documentoParameter != null) {
            sql += " and d.uuid = :documentoUuid ";
            super.addParameter("documentoUuid", documentoParameter.getValue());
        }
        // 2. Filtra por el Tipo: (Código de Archivo)
        PaginateParameters archivoTipoParameter = paginateList.getParameterByName("tipo");
        if (archivoTipoParameter != null) {
            String tipoCodigo = archivoTipoParameter.getValue();
            if(tipoCodigo != "*"){  // En el caso de que envie * se devuelven todos los archivos
                sql += " and tipo.codigo = :archivo_tipo_codigo ";
                super.addParameter("archivo_tipo_codigo", archivoTipoParameter.getValue());
            }
        }
        //hardcode: Ordenar por orden
        super.setOrderByFields("orden");

        // Define las condiciones y crea la paginación
        super.setWhereConditions(sql);
        Page<Archivo> page = super.createSql(Archivo.class, paginateList.getPage(), paginateList.getSize());
        return page;
    }
}
