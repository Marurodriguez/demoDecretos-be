package com.sm.admDecretos.Core.Service;

import com.sm.admDecretos.Core.Entity.Db.AbstractEntity;
import com.sm.admDecretos.Core.Entity.Virtual.PaginateList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


/**
 * Permite administrar las acciones en un categoria
 */
@Service
public class CategoriaService extends AbstractService<AbstractEntity> {

    /**
    @Autowired
    CategoriaRepository categoriaRepository;

    public Page<Categoria> paginate(PaginateList paginateList) throws Exception {
        // En cada petición es Obligatorio el uuid de la categoria!!

        if (paginateList.getParameterByName("categoria") == null)
            throw new Exception("No existe el parametro categoria");

        Page<Categoria> paginate = this.categoriaRepository.paginate(paginateList);
        return paginate;
    }
    */
}



