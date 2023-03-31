package com.sm.admDecretos.Core.Repository.Custom;

import com.sm.admDecretos.Core.Entity.Db.Archivo;
import com.sm.admDecretos.Core.Entity.Virtual.PaginateList;
import org.springframework.data.domain.Page;

public interface ArchivoRepositoryCustom {
    Page<Archivo> paginate(PaginateList paginateList) throws Exception;
}
